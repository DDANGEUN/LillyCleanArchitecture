package lilly.cleanarchitecture.domain.usecase.ble

import lilly.cleanarchitecture.domain.ble.BleRepository

import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanSettings
import javax.inject.Inject

class ScanBleDevicesUseCase @Inject constructor(private val repository: BleRepository) {
    fun execute(scanSettings: ScanSettings, scanFilter: ScanFilter)
            = repository.scanBleDevices(scanSettings, scanFilter)
}