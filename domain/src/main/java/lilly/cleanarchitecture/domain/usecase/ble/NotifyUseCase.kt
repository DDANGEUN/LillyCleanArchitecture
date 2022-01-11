package lilly.cleanarchitecture.domain.usecase.ble

import lilly.cleanarchitecture.domain.ble.BleRepository


class NotifyUseCase(private val repository: BleRepository) {
    fun execute() = repository.bleNotification()
}