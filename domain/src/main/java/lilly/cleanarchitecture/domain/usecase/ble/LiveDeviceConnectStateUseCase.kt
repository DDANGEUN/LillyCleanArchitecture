package lilly.cleanarchitecture.domain.usecase.ble

import lilly.cleanarchitecture.domain.ble.BleRepository


class LiveDeviceConnectStateUseCase(private val repository: BleRepository) {
    fun execute() = repository.isDeviceConnected
}