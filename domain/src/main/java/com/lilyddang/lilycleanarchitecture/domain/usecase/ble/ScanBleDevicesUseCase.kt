package com.lilyddang.lilycleanarchitecture.domain.usecase.ble

import android.content.Context
import com.lilyddang.lilycleanarchitecture.domain.ble.BleRepository
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanSettings

class ScanBleDevicesUseCase(private val repository: BleRepository) {
    fun execute(context: Context, scanSettings: ScanSettings, scanFilter: ScanFilter)
            = repository.scanBleDevices(context, scanSettings, scanFilter)
}