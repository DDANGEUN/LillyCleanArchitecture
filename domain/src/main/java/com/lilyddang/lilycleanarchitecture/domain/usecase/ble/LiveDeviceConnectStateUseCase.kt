package com.lilyddang.lilycleanarchitecture.domain.usecase.ble

import com.lilyddang.lilycleanarchitecture.domain.ble.BleRepository

class LiveDeviceConnectStateUseCase(private val repository: BleRepository) {
    fun execute(deviceNum: Int) = repository.isDeviceConnected
}