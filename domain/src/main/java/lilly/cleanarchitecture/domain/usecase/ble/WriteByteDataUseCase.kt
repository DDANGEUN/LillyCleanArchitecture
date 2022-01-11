package lilly.cleanarchitecture.domain.usecase.ble

import lilly.cleanarchitecture.domain.ble.BleRepository


class WriteByteDataUseCase(private val repository: BleRepository) {
    fun execute(sendByteData: ByteArray) = repository.writeData(sendByteData)
}