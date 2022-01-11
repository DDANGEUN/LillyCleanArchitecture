package lilly.cleanarchitecture.domain.usecase.ble

import com.polidea.rxandroidble2.RxBleDevice
import lilly.cleanarchitecture.domain.ble.BleRepository

class ConnectBleDeviceUseCase(private val repository: BleRepository) {
    fun execute(device: RxBleDevice) = repository.connectBleDevice(device)
}