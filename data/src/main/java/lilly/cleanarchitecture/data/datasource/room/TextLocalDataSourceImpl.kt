package lilly.cleanarchitecture.data.datasource.room

import kotlinx.coroutines.flow.Flow
import lilly.cleanarchitecture.data.dao.TextDao
import lilly.cleanarchitecture.data.model.TextEntity

class TextLocalDataSourceImpl(private val textDao: TextDao): TextLocalDataSource {

    override fun insertText(text: TextEntity): Long = textDao.insertTexts(text)
    override fun getAllTexts(): Flow<List<TextEntity>> = textDao.getAllTexts()
    override fun getSearchTexts(content: String): Flow<List<TextEntity>> = textDao.getTextsByContent(content)
    override fun delete(text: TextEntity) = textDao.delete(text)
    override fun deleteAllTexts() = textDao.deleteAllTexts()

}