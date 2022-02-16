package lilly.cleanarchitecture.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lilly.cleanarchitecture.data.dao.TextDao
import lilly.cleanarchitecture.data.datasource.room.TextLocalDataSource
import lilly.cleanarchitecture.data.datasource.room.TextLocalDataSourceImpl
import lilly.cleanarchitecture.data.datasource.start.StartDataSource
import lilly.cleanarchitecture.data.datasource.start.StartDataSourceImpl
import lilly.cleanarchitecture.data.utils.PreferenceManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideTextLocalDataSource(textDao: TextDao) : TextLocalDataSource {
        return TextLocalDataSourceImpl(textDao)
    }

    @Provides
    @Singleton
    fun provideStartDataSource(preferenceManager: PreferenceManager) : StartDataSource {
        return StartDataSourceImpl(preferenceManager)
    }


}