package lilly.cleanarchitecture.domain.usecase.ble

import lilly.cleanarchitecture.domain.ble.BleRepository
import javax.inject.Inject


class ReadUseCase @Inject constructor(private val repository: BleRepository) {
    fun execute() = repository.bleRead()
}