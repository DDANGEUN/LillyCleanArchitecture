package com.lilyddang.lilycleanarchitecture.di

import androidx.room.Room
import com.lilyddang.lilycleanarchitecture.data.dao.TextDao
import com.lilyddang.lilycleanarchitecture.data.database.AppDatabase
import com.lilyddang.lilycleanarchitecture.data.datasource.TextLocalDataSource
import com.lilyddang.lilycleanarchitecture.data.datasource.TextLocalDataSourceImpl
import com.lilyddang.lilycleanarchitecture.data.repository.TextRepositoryImpl
import com.lilyddang.lilycleanarchitecture.domain.repository.TextRepository
import com.lilyddang.lilycleanarchitecture.domain.usecase.DeleteTextUseCase
import com.lilyddang.lilycleanarchitecture.domain.usecase.GetAllLocalTextsUseCase
import com.lilyddang.lilycleanarchitecture.domain.usecase.GetSearchTextsUseCase
import com.lilyddang.lilycleanarchitecture.domain.usecase.InsertTextUseCase
import com.lilyddang.lilycleanarchitecture.viewmodel.BleViewModel
import com.lilyddang.lilycleanarchitecture.viewmodel.MainViewModel
import com.lilyddang.lilycleanarchitecture.viewmodel.RoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { RoomViewModel(get(),get(),get(),get()) }
    viewModel { BleViewModel() }
}

val repositoryModule: Module = module {
    single<TextRepository> { TextRepositoryImpl(get()) }
}

val localDataModule: Module = module {
    single<TextLocalDataSource> { TextLocalDataSourceImpl(get()) }
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
}
val apiModule: Module = module {
    //..
}

