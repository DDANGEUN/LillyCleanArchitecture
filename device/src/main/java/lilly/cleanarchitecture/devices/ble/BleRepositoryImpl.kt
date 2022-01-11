package lilly.cleanarchitecture.devices.ble

import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanResult
import com.polidea.rxandroidble2.scan.ScanSettings
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import lilly.cleanarchitecture.devices.CHARACTERISTIC_COMMAND_STRING
import lilly.cleanarchitecture.devices.CHARACTERISTIC_RESPONSE_STRING
import lilly.cleanarchitecture.domain.ble.BleRepository
import lilly.cleanarchitecture.domain.utils.DeviceEvent
import java.util.*

class BleRepositoryImpl(private val rxBleClient: RxBleClient) : BleRepository {
    private var rxBleConnection: RxBleConnection? = null
    private var conStateDisposable: Disposable? = null
    private var mConnectSubscription: Disposable? = null

    override var deviceConnectionEvent = MutableSharedFlow<DeviceEvent<Boolean>>()
    override var isDeviceConnected = MutableStateFlow(false)
    private var connectedBleDevice: RxBleDevice? = null

    /**
     * Scan
     */
    override fun scanBleDevices(
        settings: ScanSettings,
        scanFilter: ScanFilter
    ): Observable<ScanResult> = rxBleClient.scanBleDevices(settings, scanFilter)

    /**
     * Connect & Discover Services
     * @Saved rxBleConnection
     */
    override fun connectBleDevice(
        device: RxBleDevice
    ) {
        conStateDisposable = device.observeConnectionStateChanges()
            .subscribe(
                { connectionState ->
                    connectionStateListener(device, connectionState)
                }
            ) { throwable ->
                throwable.printStackTrace()
            }
        mConnectSubscription = device.establishConnection(false)
            .flatMapSingle { _rxBleConnection ->
                // All GATT operations are done through the rxBleConnection.
                rxBleConnection = _rxBleConnection
                // Discover services
                _rxBleConnection.discoverServices()
            }.subscribe({
                // Services
            }, {
            })
    }


    /**
     * Connection State Changed Listener
     */
    private fun connectionStateListener(
        device: RxBleDevice,
        connectionState: RxBleConnection.RxBleConnectionState
    ) {
        when (connectionState) {
            RxBleConnection.RxBleConnectionState.CONNECTED -> {
                CoroutineScope(Dispatchers.IO).launch {
                    deviceConnectionEvent.emit(DeviceEvent.deviceConnectionEvent(device.name ?: "", true))
                    isDeviceConnected.value = true
                    connectedBleDevice = device
                }
            }
            RxBleConnection.RxBleConnectionState.CONNECTING -> {
            }
            RxBleConnection.RxBleConnectionState.DISCONNECTED -> {
                conStateDisposable?.dispose()
                CoroutineScope(Dispatchers.IO).launch {
                    deviceConnectionEvent.emit(DeviceEvent.deviceConnectionEvent(device.name ?: "", false))
                    isDeviceConnected.value = false
                    connectedBleDevice = null
                }
            }
            RxBleConnection.RxBleConnectionState.DISCONNECTING -> {
            }
        }
    }


    /**
     * Notification
     */
    override fun bleNotification() = rxBleConnection
        ?.setupNotification(UUID.fromString(CHARACTERISTIC_RESPONSE_STRING))
        ?.doOnNext { notificationObservable ->
            // Notification has been set up
        }
        ?.flatMap { notificationObservable -> notificationObservable }

    /**
     * Read
     */
    override fun bleRead() =
        rxBleConnection?.readCharacteristic(UUID.fromString(CHARACTERISTIC_RESPONSE_STRING))

    /**
     * Write Data
     */
    override fun writeData(sendByteData: ByteArray) =
        rxBleConnection?.writeCharacteristic(
            UUID.fromString(CHARACTERISTIC_COMMAND_STRING),
            sendByteData
        )

    /**
     * Disconnect Device
     */
    override fun disconnectBleDevice(){
        mConnectSubscription?.dispose()
    }

    override fun getDeviceName(): String = connectedBleDevice?.name?: ""
}
