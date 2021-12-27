package com.lilyddang.lilycleanarchitecture.viewmodel


import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.viewModelScope
import com.lilyddang.lilycleanarchitecture.base.BaseViewModel
import com.lilyddang.lilycleanarchitecture.domain.usecase.ble.ScanBleDevicesUseCase
import com.lilyddang.lilycleanarchitecture.utils.MutableEventFlow
import com.lilyddang.lilycleanarchitecture.utils.asEventFlow
import com.polidea.rxandroidble2.exceptions.BleScanException
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanResult
import com.polidea.rxandroidble2.scan.ScanSettings
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule

class BleViewModel(
    private val scanBleDevicesUseCase: ScanBleDevicesUseCase
) : BaseViewModel() {

    val statusText = ObservableField("Press the Scan Tap to Start Ble Scan.")
    private var mScanSubscription: Disposable? = null
    val isScanning = ObservableBoolean(false)
    var isConnected = ObservableBoolean(false)

    // scan results
    var scanResults = HashMap<String, ScanResult>()
    var scanResultSize = ObservableInt(0)

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()


    fun startScan() {
        //scan filter
        val scanFilter: ScanFilter = ScanFilter.Builder()
            //.setServiceUuid(ParcelUuid(UUID.fromString(SERVICE_STRING)))
            //.setDeviceName("")
            .build()
        // scan settings
        // set low power scan mode
        val settings: ScanSettings = ScanSettings.Builder()
            //.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
            .build()


        scanResults = HashMap<String, ScanResult>() //list 초기화


        mScanSubscription =
            scanBleDevicesUseCase.execute(settings, scanFilter)
                .subscribe({ scanResult ->
                    addScanResult(scanResult)
                }, { throwable ->
                    if (throwable is BleScanException) {
                        event(Event.BleScanException(throwable.reason))
                    } else {
                        event(Event.ShowNotification("UNKNOWN ERROR", "error"))
                    }
                })

        isScanning.set(true)

        Timer("Scan", false).schedule(5000L) { stopScan() }

    }

    fun stopScan() {
        mScanSubscription?.dispose()
        isScanning.set(false)
        if (scanResults.isEmpty()) {
            event(Event.ListUpdate(scanResults))
            scanResultSize.set(0)
        }
    }

    /**
     * Add scan result
     */
    private fun addScanResult(result: ScanResult) {
        val device = result.bleDevice
        val deviceAddress = device.macAddress
        if (scanResults.containsKey(deviceAddress)) return
        scanResults[deviceAddress] = result
        scanResultSize.set(scanResults.size)
        event(Event.ListUpdate(scanResults))
    }

    override fun onCleared() {
        super.onCleared()
        mScanSubscription?.dispose()
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        data class BleScanException(val reason: Int) : Event()
        data class ListUpdate(val reults: HashMap<String, ScanResult>) : Event()
        data class ShowNotification(val message: String, val type: String) : Event()
    }

}