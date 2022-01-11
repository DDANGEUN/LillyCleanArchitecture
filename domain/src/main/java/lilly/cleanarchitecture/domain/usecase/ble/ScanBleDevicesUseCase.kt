package lilly.cleanarchitecture.domain.usecase.ble

import lilly.cleanarchitecture.domain.ble.BleRepository

import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanSettings

class ScanBleDevicesUseCase(private val repository: BleRepository) {
    fun execute(scanSettings: ScanSettings, scanFilter: ScanFilter)
            = repository.scanBleDevices(scanSettings, scanFilter)
}