package com.lilyddang.lilycleanarchitecture.domain.usecase.ble

import com.lilyddang.lilycleanarchitecture.domain.ble.BleRepository

class WriteByteDataUseCase(private val repository: BleRepository) {
    fun execute(sendByteData: ByteArray) = repository.writeData(sendByteData)
}