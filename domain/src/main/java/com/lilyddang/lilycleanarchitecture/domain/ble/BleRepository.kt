package com.lilyddang.lilycleanarchitecture.domain.ble

import com.lilyddang.lilycleanarchitecture.domain.utils.DeviceEvent
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanResult
import com.polidea.rxandroidble2.scan.ScanSettings
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

interface BleRepository {

    var deviceConnectionEvent: MutableSharedFlow<DeviceEvent<Boolean>>
    var isDeviceConnected: MutableStateFlow<Boolean>

    fun scanBleDevices(settings: ScanSettings, scanFilter: ScanFilter): Observable<ScanResult>
    fun connectBleDevice(device: RxBleDevice)
    fun disconnectBleDevice()
    fun bleNotification(): Observable<ByteArray>?
    fun bleRead(): Single<ByteArray>?
    fun writeData(sendByteData: ByteArray): Single<ByteArray>?
    fun getDeviceName(): String

}