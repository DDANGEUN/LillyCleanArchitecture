package lilly.cleanarchitecture.domain.usecase.ble

import lilly.cleanarchitecture.domain.ble.BleRepository


class ReadUseCase(private val repository: BleRepository) {
    fun execute() = repository.bleRead()
}