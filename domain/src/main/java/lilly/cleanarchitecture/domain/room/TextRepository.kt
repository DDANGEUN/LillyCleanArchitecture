package lilly.cleanarchitecture.domain.room

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import lilly.cleanarchitecture.domain.room.model.TextItem

interface TextRepository {

    fun getAllLocalTexts(): Flowable<List<TextItem>>
    fun getLocalSearchTexts(query: String): Flowable<List<TextItem>>
    fun insertText(textItem: TextItem): Single<Long>
    fun deleteText(textItem: TextItem): Completable
    fun deleteAllTexts(): Completable

}