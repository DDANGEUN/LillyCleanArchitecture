package lilly.cleanarchitecture.viewmodel


import android.os.ParcelUuid
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.exceptions.BleScanException
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanResult
import com.polidea.rxandroidble2.scan.ScanSettings
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import lilly.cleanarchitecture.base.BaseViewModel
import lilly.cleanarchitecture.devices.SERVICE_STRING
import lilly.cleanarchitecture.domain.usecase.ble.*
import lilly.cleanarchitecture.domain.utils.DeviceEvent
import java.nio.charset.Charset
import java.util.*
import kotlin.concurrent.schedule

class BleViewModel(
    private val scanBleDevicesUseCase: ScanBleDevicesUseCase,
    private val connectBleDeviceUseCase: ConnectBleDeviceUseCase,
    private val disconnectBleDeviceUseCase: DisconnectBleDeviceUseCase,
    private val notifyUseCase: NotifyUseCase,
    private val writeByteDataUseCase: WriteByteDataUseCase,
    private val getDeviceNameUseCase: GetDeviceNameUseCase,
    deviceConnectionEventUseCase: DeviceConnectionEventUseCase,
    liveDeviceConnectStateUseCase: LiveDeviceConnectStateUseCase
) : BaseViewModel() {

    val statusText = ObservableField("Press the Scan Tap to Start Ble Scan.")
    private var mScanSubscription: Disposable? = null
    private var mNotificationSubscription: Disposable? = null
    val isScanning = ObservableBoolean(false)

    var isConnected = liveDeviceConnectStateUseCase.execute().asStateFlow()
    val deviceConnectionEvent: SharedFlow<DeviceEvent<Boolean>> =
        deviceConnectionEventUseCase.execute().asSharedFlow()

    var scanResults = HashMap<String, ScanResult>()

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()


    val notify = ObservableBoolean(false)


    fun startScan() {
        //scan filter
        statusText.set("Scanning...")
        val scanFilter: ScanFilter = ScanFilter.Builder()
            .setServiceUuid(ParcelUuid(UUID.fromString(SERVICE_STRING)))
            //.setDeviceName("")
            .build()
        // scan settings
        // set low power scan mode
        val settings: ScanSettings = ScanSettings.Builder()
            //.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
            .build()


        scanResults = HashMap<String, ScanResult>() //list 초기화
        isScanning.set(true)

        mScanSubscription =
            scanBleDevicesUseCase.execute(settings, scanFilter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ scanResult ->
                    addScanResult(scanResult)
                }, { throwable ->
                    if (throwable is BleScanException) {
                        event(Event.BleScanException(throwable.reason))
                    } else {
                        event(Event.ShowNotification("UNKNOWN ERROR", "error"))
                    }
                })


        Timer("Scan", false).schedule(5000L) { stopScan() }

    }

    fun stopScan() {
       //event(Event.ListUpdate(scanResults))
        statusText.set("Finished Scanning.")
        mScanSubscription?.dispose()
        isScanning.set(false)
        if (scanResults.isEmpty()) {
            event(Event.ListUpdate(scanResults))
        }
    }

    /**
     * Add scan result
     */
    private fun addScanResult(result: ScanResult) {
        val device = result.bleDevice
        val deviceAddress = device.macAddress
        scanResults[deviceAddress] = result
        event(Event.ListUpdate(scanResults))
    }

    /**
     * Connect & Disconnect BleDevice.
     */
    fun connectDevice(device: RxBleDevice) = connectBleDeviceUseCase.execute(device)
    fun disconnectDevice() = disconnectBleDeviceUseCase.execute()

    /**
     * Notification
     */
    fun notifyToggle(){
        if(!notify.get()){
            notify.set(true)
            mNotificationSubscription = notifyUseCase.execute()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ bytes->
                    val hexString: String = bytes.joinToString(separator = " ") {
                        String.format("%02X", it)
                    }
                    Log.d("read",hexString)
                    event(Event.ReadLogUpdate(hexString))
                },{
                    event(Event.ShowNotification("${it.message}", "error"))
                    mNotificationSubscription?.dispose()
                    notify.set(false)
                })
        }else{
            notify.set(false)
            mNotificationSubscription?.dispose()
        }
    }
    fun writeData(data:String, type:String){
        var sendByteData: ByteArray? = null
        when(type){
            "string" -> {
                sendByteData = data.toByteArray(Charset.defaultCharset())
            }
            "byte" -> {
                sendByteData = hexStringToByteArray(data)
            }
        }
        if (sendByteData != null) {

            writeByteDataUseCase.execute(sendByteData)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ bytes->
                    val hexString: String = bytes.joinToString(separator = " ") {
                        String.format("%02X", it)
                    }
                    event(Event.ShowNotification("`$hexString` is written.","success"))

                },{
                    event(Event.ShowNotification("${it.message}", "error"))
                })?.let { addDisposable(it) }
        }
    }
    fun getDeviceName() = getDeviceNameUseCase.execute()

    private fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(s[i], 16) shl 4)
                    + Character.digit(s[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }

    override fun onCleared() {
        super.onCleared()
        mScanSubscription?.dispose()
        mNotificationSubscription?.dispose()
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        data class BleScanException(val reason: Int) : Event()
        data class ListUpdate(val results: HashMap<String, ScanResult>) : Event()
        data class ReadLogUpdate(val hexString: String) : Event()
        data class ShowNotification(val message: String, val type: String) : Event()
    }

}