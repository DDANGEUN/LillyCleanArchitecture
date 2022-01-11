package lilly.cleanarchitecture.domain.usecase.ble

import lilly.cleanarchitecture.domain.ble.BleRepository


class DisconnectBleDeviceUseCase(private val repository: BleRepository) {
    fun execute() = repository.disconnectBleDevice()
}