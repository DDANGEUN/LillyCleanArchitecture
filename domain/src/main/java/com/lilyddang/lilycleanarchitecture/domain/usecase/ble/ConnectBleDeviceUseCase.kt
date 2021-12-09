package com.lilyddang.lilycleanarchitecture.domain.usecase.ble

import com.lilyddang.lilycleanarchitecture.domain.ble.BleRepository
import com.polidea.rxandroidble2.RxBleDevice

class ConnectBleDeviceUseCase(private val repository: BleRepository) {
    fun execute(device: RxBleDevice) = repository.connectBleDevice(device)
}