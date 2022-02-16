package lilly.cleanarchitecture.di

import android.content.Context
import com.polidea.rxandroidble2.RxBleClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DeviceModule {
    @Provides
    @Singleton
    fun provideRxBleClient(@ApplicationContext context: Context): RxBleClient{
        return RxBleClient.create(context)
    }
}