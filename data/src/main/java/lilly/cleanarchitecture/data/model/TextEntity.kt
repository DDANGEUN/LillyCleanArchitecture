package lilly.cleanarchitecture.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "text")
data class TextEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val time: String,
    val content: String
)