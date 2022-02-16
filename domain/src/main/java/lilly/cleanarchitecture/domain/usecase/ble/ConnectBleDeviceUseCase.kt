package lilly.cleanarchitecture.domain.usecase.ble

import com.polidea.rxandroidble2.RxBleDevice
import lilly.cleanarchitecture.domain.ble.BleRepository
import javax.inject.Inject

class ConnectBleDeviceUseCase @Inject constructor(private val repository: BleRepository) {
    fun execute(device: RxBleDevice) = repository.connectBleDevice(device)
}