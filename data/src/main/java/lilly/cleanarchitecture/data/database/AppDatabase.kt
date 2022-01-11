package lilly.cleanarchitecture.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import lilly.cleanarchitecture.data.dao.TextDao
import lilly.cleanarchitecture.data.model.TextEntity

@Database(
    entities = [TextEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun textDao(): TextDao
}