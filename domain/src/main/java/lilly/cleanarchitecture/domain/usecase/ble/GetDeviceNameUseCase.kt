package lilly.cleanarchitecture.domain.usecase.ble

import lilly.cleanarchitecture.domain.ble.BleRepository

class GetDeviceNameUseCase(private val repository: BleRepository) {
    fun execute() = repository.getDeviceName()
}