package lilly.cleanarchitecture.di

import android.content.Context
import androidx.room.Room
import com.polidea.rxandroidble2.RxBleClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import lilly.cleanarchitecture.data.dao.TextDao
import lilly.cleanarchitecture.data.database.AppDatabase
import lilly.cleanarchitecture.data.utils.PreferenceManager
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "text.db")
        .build()

    @Singleton
    @Provides
    fun provideTextDao(appDatabase: AppDatabase): TextDao = appDatabase.textDao()

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager {
        return PreferenceManager(context)
    }
}