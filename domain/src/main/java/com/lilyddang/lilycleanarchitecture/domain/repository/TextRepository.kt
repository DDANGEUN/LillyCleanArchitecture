package com.lilyddang.lilycleanarchitecture.domain.repository

import com.lilyddang.lilycleanarchitecture.domain.model.TextItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface TextRepository {

    fun getAllLocalTexts(): Flowable<List<TextItem>>
    fun getLocalSearchTexts(query: String): Flowable<List<TextItem>>
    fun insertText(textItem: TextItem): Single<Long>
    fun deleteText(textItem: TextItem): Completable
    fun deleteAllTexts(): Completable

}