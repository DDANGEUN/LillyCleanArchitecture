package lilly.cleanarchitecture.domain.room

import kotlinx.coroutines.flow.Flow
import lilly.cleanarchitecture.domain.room.model.TextItem

interface TextRepository {

    fun getAllLocalTexts(): Flow<List<TextItem>>
    fun getLocalSearchTexts(query: String): Flow<List<TextItem>>
    fun insertText(textItem: TextItem): Long
    fun deleteText(textItem: TextItem)
    fun deleteAllTexts()

}