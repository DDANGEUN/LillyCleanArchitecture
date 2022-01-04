package com.lilyddang.lilycleanarchitecture.domain.usecase.ble

import com.lilyddang.lilycleanarchitecture.domain.ble.BleRepository

class NotifyUseCase(private val repository: BleRepository) {
    fun execute() = repository.bleNotification()
}