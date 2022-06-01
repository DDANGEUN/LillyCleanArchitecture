package lilly.cleanarchitecture.data.datasource.room

import kotlinx.coroutines.flow.Flow
import lilly.cleanarchitecture.data.model.TextEntity

interface TextLocalDataSource {
    fun insertText(text: TextEntity): Long
    fun getAllTexts(): Flow<List<TextEntity>>
    fun getSearchTexts(content: String): Flow<List<TextEntity>>
    fun delete(text: TextEntity)
    fun deleteAllTexts()
}