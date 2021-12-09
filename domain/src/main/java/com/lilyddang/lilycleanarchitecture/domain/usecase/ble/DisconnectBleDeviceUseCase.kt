package com.lilyddang.lilycleanarchitecture.domain.usecase.ble

import com.lilyddang.lilycleanarchitecture.domain.ble.BleRepository

class DisconnectBleDeviceUseCase(private val repository: BleRepository) {
    fun execute() = repository.disconnectBleDevice()
}