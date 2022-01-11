package lilly.cleanarchitecture.data.datasource.room

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import lilly.cleanarchitecture.data.model.TextEntity

interface TextLocalDataSource {
    fun insertText(text: TextEntity): Single<Long>
    fun getAllTexts(): Flowable<List<TextEntity>>
    fun getSearchTexts(content: String): Single<List<TextEntity>>
    fun delete(text: TextEntity): Completable
    fun deleteAllTexts(): Completable
}