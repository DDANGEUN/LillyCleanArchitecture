package lilly.cleanarchitecture.di

import dagger.Provides
import dagger.Module
import com.polidea.rxandroidble2.RxBleClient
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lilly.cleanarchitecture.data.datasource.room.TextLocalDataSource
import lilly.cleanarchitecture.data.datasource.start.StartDataSource
import lilly.cleanarchitecture.data.repository.StartRepositoryImpl
import lilly.cleanarchitecture.data.repository.TextRepositoryImpl
import lilly.cleanarchitecture.devices.ble.BleRepositoryImpl
import lilly.cleanarchitecture.domain.ble.BleRepository
import lilly.cleanarchitecture.domain.room.TextRepository
import lilly.cleanarchitecture.domain.sharedpreference.StartRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTextRepository(textLocalDataSource: TextLocalDataSource) : TextRepository {
        return TextRepositoryImpl(textLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideStartRepository(startDataSource: StartDataSource) : StartRepository {
        return StartRepositoryImpl(startDataSource)
    }

    @Provides
    @Singleton
    fun provideBleRepository(rxBleClient: RxBleClient) : BleRepository {
        return BleRepositoryImpl(rxBleClient)
    }

}