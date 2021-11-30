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
import com.lilyddang.lilycleanarchitecture.domain.repository.StartRepository
import com.lilyddang.lilycleanarchitecture.domain.repository.TextRepository
import com.lilyddang.lilycleanarchitecture.domain.usecase.*
import com.lilyddang.lilycleanarchitecture.viewmodel.BleViewModel
import com.lilyddang.lilycleanarchitecture.viewmodel.MainViewModel
import com.lilyddang.lilycleanarchitecture.viewmodel.RoomViewModel
import com.lilyddang.lilycleanarchitecture.viewmodel.StartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { RoomViewModel(get(),get(),get(),get()) }
    viewModel { BleViewModel() }
    viewModel { StartViewModel(get(),get()) }
}

val repositoryModule: Module = module {
    single<TextRepository> { TextRepositoryImpl(get()) }
    single<StartRepository> { StartRepositoryImpl(get()) }
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
    single<InsertTextUseCase> { InsertTextUseCase(get()) }
    single<DeleteTextUseCase> { DeleteTextUseCase(get()) }
    single<GetAllLocalTextsUseCase> { GetAllLocalTextsUseCase(get()) }
    single<GetSearchTextsUseCase> { GetSearchTextsUseCase(get()) }
    single<GetInfoSkipUseCase>{ GetInfoSkipUseCase(get()) }
    single<InsertInfoSkipUseCase>{ InsertInfoSkipUseCase(get()) }
}
val apiModule: Module = module {
    //..
}

