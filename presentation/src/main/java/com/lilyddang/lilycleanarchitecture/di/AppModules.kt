package com.lilyddang.lilycleanarchitecture.di

import androidx.room.Room
import com.lilyddang.lilycleanarchitecture.data.dao.TextDao
import com.lilyddang.lilycleanarchitecture.data.database.AppDatabase
import com.lilyddang.lilycleanarchitecture.data.datasource.room.TextLocalDataSource
import com.lilyddang.lilycleanarchitecture.data.datasource.room.TextLocalDataSourceImpl
import com.lilyddang.lilycleanarchitecture.data.datasource.start.StartDataSource
import com.lilyddang.lilycleanarchitecture.data.datasource.start.StartDataSourceImpl
import com.lilyddang.lilycleanarchitecture.data.repository.StartRepositoryImpl
import com.lilyddang.lilycleanarchitecture.data.repository.TextRepositoryImpl
import com.lilyddang.lilycleanarchitecture.data.utils.PreferenceManager
import com.lilyddang.lilycleanarchitecture.devices.ble.BleRepositoryImpl
import com.lilyddang.lilycleanarchitecture.domain.ble.BleRepository
import com.lilyddang.lilycleanarchitecture.domain.sharedpreference.StartRepository
import com.lilyddang.lilycleanarchitecture.domain.room.TextRepository
import com.lilyddang.lilycleanarchitecture.domain.usecase.ble.*
import com.lilyddang.lilycleanarchitecture.domain.usecase.room.DeleteTextUseCase
import com.lilyddang.lilycleanarchitecture.domain.usecase.room.GetAllLocalTextsUseCase
import com.lilyddang.lilycleanarchitecture.domain.usecase.room.GetSearchTextsUseCase
import com.lilyddang.lilycleanarchitecture.domain.usecase.room.InsertTextUseCase
import com.lilyddang.lilycleanarchitecture.domain.usecase.sharedpreference.GetInfoSkipUseCase
import com.lilyddang.lilycleanarchitecture.domain.usecase.sharedpreference.InsertInfoSkipUseCase
import com.lilyddang.lilycleanarchitecture.ui.ble.ReadFragment
import com.lilyddang.lilycleanarchitecture.ui.ble.ScanFragment
import com.lilyddang.lilycleanarchitecture.viewmodel.BleViewModel
import com.lilyddang.lilycleanarchitecture.viewmodel.MainViewModel
import com.lilyddang.lilycleanarchitecture.viewmodel.RoomViewModel
import com.lilyddang.lilycleanarchitecture.viewmodel.StartViewModel
import com.polidea.rxandroidble2.RxBleClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { RoomViewModel(get(),get(),get(),get()) }
    viewModel { BleViewModel(get(),get(),get(),get(),get(),get(),get()) }
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
}
val deviceModule: Module = module {
    single<RxBleClient>{ RxBleClient.create(get()) }
}
val fragmentModule = module{
    single{
        ScanFragment()
    }
    single{
        ReadFragment()
    }
}
val apiModule: Module = module {
    //..
}

