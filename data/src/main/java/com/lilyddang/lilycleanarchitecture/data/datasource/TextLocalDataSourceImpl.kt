package com.lilyddang.lilycleanarchitecture.data.datasource

import com.lilyddang.lilycleanarchitecture.data.dao.TextDao
import com.lilyddang.lilycleanarchitecture.data.datasource.TextLocalDataSource
import com.lilyddang.lilycleanarchitecture.data.model.TextEntity
import io.reactivex.Completable
import io.reactivex.Single

class TextLocalDataSourceImpl(private val textDao: TextDao): TextLocalDataSource {

    override fun insertText(text: TextEntity): Single<Long> = textDao.insertTexts(text)
    override fun getAllTexts(): Single<List<TextEntity>> = textDao.getAllTexts()
    override fun getSearchTexts(content: String): Single<List<TextEntity>> = textDao.getTextsByContent(content)
    override fun delete(text: TextEntity): Completable = textDao.delete(text)
    override fun deleteAllTexts(): Completable = textDao.deleteAllTexts()

}