package lilly.cleanarchitecture.domain.usecase.ble

import lilly.cleanarchitecture.domain.ble.BleRepository
import javax.inject.Inject


class WriteByteDataUseCase @Inject constructor(private val repository: BleRepository) {
    fun execute(sendByteData: ByteArray) = repository.writeData(sendByteData)
}