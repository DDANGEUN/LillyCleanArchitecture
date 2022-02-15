package lilly.cleanarchitecture.di

import androidx.room.Room

import com.polidea.rxandroidble2.RxBleClient
import lilly.cleanarchitecture.data.dao.TextDao
import lilly.cleanarchitecture.data.database.AppDatabase
import lilly.cleanarchitecture.data.datasource.room.TextLocalDataSource
import lilly.cleanarchitecture.data.datasource.room.TextLocalDataSourceImpl
import lilly.cleanarchitecture.data.datasource.start.StartDataSource
import lilly.cleanarchitecture.data.datasource.start.StartDataSourceImpl
import lilly.cleanarchitecture.data.repository.StartRepositoryImpl
import lilly.cleanarchitecture.data.repository.TextRepositoryImpl
import lilly.cleanarchitecture.data.utils.PreferenceManager
import lilly.cleanarchitecture.devices.ble.BleRepositoryImpl
import lilly.cleanarchitecture.domain.ble.BleRepository
import lilly.cleanarchitecture.domain.room.TextRepository
import lilly.cleanarchitecture.domain.sharedpreference.StartRepository
import lilly.cleanarchitecture.domain.usecase.ble.*
import lilly.cleanarchitecture.domain.usecase.room.DeleteTextUseCase
import lilly.cleanarchitecture.domain.usecase.room.GetAllLocalTextsUseCase
import lilly.cleanarchitecture.domain.usecase.room.GetSearchTextsUseCase
import lilly.cleanarchitecture.domain.usecase.room.InsertTextUseCase
import lilly.cleanarchitecture.domain.usecase.sharedpreference.GetInfoSkipUseCase
import lilly.cleanarchitecture.domain.usecase.sharedpreference.InsertInfoSkipUseCase
import lilly.cleanarchitecture.ui.ble.ReadFragment
import lilly.cleanarchitecture.ui.ble.ScanFragment
import lilly.cleanarchitecture.viewmodel.BleViewModel
import lilly.cleanarchitecture.viewmodel.MainViewModel
import lilly.cleanarchitecture.viewmodel.RoomViewModel
import lilly.cleanarchitecture.viewmodel.StartViewModel
import org.koin.core.module.Module
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { RoomViewModel(get(),get(),get(),get()) }
    viewModel { BleViewModel(get(),get(),get(),get(),get(),get(),get(),get()) }
    viewModel { StartViewModel(get(),get()) }
}

val repositoryModule: Module = module {
    single<TextRepository> { TextRepositoryImpl(get()) }
    single<StartRepository> { StartRepositoryImpl(get()) }
    single<BleRepository> { BleRepositoryImpl(get()) }
}

val localDataModule: Module = module {
    single<TextLocalDataSource> { TextLocalDataSourceImpl(get()) }
    single<StartDataSource> { StartDataSourceImpl(get()) }
    single<PreferenceManager> { PreferenceManager(get()) }
    single<TextDao> { get<AppDatabase>().textDao() }
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java, "Text.db"
        )
            .build()
    }
}
val useCaseModule: Module = module {
    // Room
    single<InsertTextUseCase> { InsertTextUseCase(get()) }
    single<DeleteTextUseCase> { DeleteTextUseCase(get()) }
    single<GetAllLocalTextsUseCase> { GetAllLocalTextsUseCase(get()) }
    single<GetSearchTextsUseCase> { GetSearchTextsUseCase(get()) }

    // SharedPreference
    single<GetInfoSkipUseCase>{ GetInfoSkipUseCase(get()) }
    single<InsertInfoSkipUseCase>{ InsertInfoSkipUseCase(get()) }

    // Ble
    single<ScanBleDevicesUseCase>{ ScanBleDevicesUseCase(get()) }
    single<ConnectBleDeviceUseCase>{ ConnectBleDeviceUseCase(get()) }
    single<DeviceConnectionEventUseCase>{ DeviceConnectionEventUseCase(get()) }
    single<LiveDeviceConnectStateUseCase>{ LiveDeviceConnectStateUseCase(get()) }
    single<DisconnectBleDeviceUseCase>{ DisconnectBleDeviceUseCase(get())}
    single<NotifyUseCase>{ NotifyUseCase(get()) }
    single<ReadUseCase>{ ReadUseCase(get()) }
    single<WriteByteDataUseCase>{ WriteByteDataUseCase(get()) }
    single<GetDeviceNameUseCase>{ GetDeviceNameUseCase(get()) }
}
val deviceModule: Module = module {
    single<RxBleClient>{ RxBleClient.create(get()) }
}
val apiModule: Module = module {
    //..
}

