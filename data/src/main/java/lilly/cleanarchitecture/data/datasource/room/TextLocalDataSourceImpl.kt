package lilly.cleanarchitecture.data.datasource.room

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import lilly.cleanarchitecture.data.dao.TextDao
import lilly.cleanarchitecture.data.model.TextEntity

class TextLocalDataSourceImpl(private val textDao: TextDao): TextLocalDataSource {

    override fun insertText(text: TextEntity): Single<Long> = textDao.insertTexts(text)
    override fun getAllTexts(): Flowable<List<TextEntity>> = textDao.getAllTexts()
    override fun getSearchTexts(content: String): Single<List<TextEntity>> = textDao.getTextsByContent(content)
    override fun delete(text: TextEntity): Completable = textDao.delete(text)
    override fun deleteAllTexts(): Completable = textDao.deleteAllTexts()

}