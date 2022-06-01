package lilly.cleanarchitecture.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lilly.cleanarchitecture.data.datasource.room.TextLocalDataSource
import lilly.cleanarchitecture.data.mapper.map
import lilly.cleanarchitecture.data.mapper.mapperToText
import lilly.cleanarchitecture.domain.room.TextRepository
import lilly.cleanarchitecture.domain.room.model.TextItem

class TextRepositoryImpl(private val textLocalDataSource: TextLocalDataSource): TextRepository {
    override fun getAllLocalTexts(): Flow<List<TextItem>>
        = textLocalDataSource.getAllTexts().map { localTexts-> mapperToText(localTexts) }

    override fun getLocalSearchTexts(query: String): Flow<List<TextItem>> {
        return textLocalDataSource.getSearchTexts(query).map { localTexts -> mapperToText(localTexts) }
    }

    override fun insertText(textItem: TextItem): Long = textLocalDataSource.insertText(textItem.map())
    override fun deleteText(textItem: TextItem) = textLocalDataSource.delete(textItem.map())
    override fun deleteAllTexts() = textLocalDataSource.deleteAllTexts()

}