package lilly.cleanarchitecture.domain.usecase.ble

import lilly.cleanarchitecture.domain.ble.BleRepository


class DeviceConnectionEventUseCase(private val repository: BleRepository) {
    fun execute() = repository.deviceConnectionEvent
}