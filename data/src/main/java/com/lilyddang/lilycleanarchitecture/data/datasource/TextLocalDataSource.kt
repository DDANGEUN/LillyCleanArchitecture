package com.lilyddang.lilycleanarchitecture.data.datasource

import com.lilyddang.lilycleanarchitecture.data.model.TextEntity
import io.reactivex.Completable
import io.reactivex.Single

interface TextLocalDataSource {
    fun insertText(text: TextEntity): Single<Long>
    fun getAllTexts(): Single<List<TextEntity>>
    fun getSearchTexts(content: String): Single<List<TextEntity>>
    fun delete(text: TextEntity): Completable
    fun deleteAllTexts(): Completable
}