package com.lilyddang.lilycleanarchitecture.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lilyddang.lilycleanarchitecture.data.dao.TextDao
import com.lilyddang.lilycleanarchitecture.data.model.TextEntity

@Database(
    entities = [TextEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun textDao(): TextDao
}