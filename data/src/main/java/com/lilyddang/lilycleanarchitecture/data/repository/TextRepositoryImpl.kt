package com.lilyddang.lilycleanarchitecture.data.repository

import com.lilyddang.lilycleanarchitecture.data.NO_DATA_FROM_LOCAL_DB
import com.lilyddang.lilycleanarchitecture.data.datasource.TextLocalDataSource
import com.lilyddang.lilycleanarchitecture.data.mapper.map
import com.lilyddang.lilycleanarchitecture.data.mapper.mapperToText
import com.lilyddang.lilycleanarchitecture.domain.model.TextItem
import com.lilyddang.lilycleanarchitecture.domain.repository.TextRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class TextRepositoryImpl(private val textLocalDataSource: TextLocalDataSource): TextRepository {
    override fun getAllLocalTexts(): Flowable<List<TextItem>> {
        return textLocalDataSource.getAllTexts().flatMapPublisher { localTexts->
            if (localTexts.isEmpty()) {
                Flowable.error(IllegalStateException(NO_DATA_FROM_LOCAL_DB))
            } else {
                Flowable.just(mapperToText(localTexts))
            }
        }
    }

    override fun getLocalSearchTexts(query: String): Flowable<List<TextItem>> {
        return textLocalDataSource.getSearchTexts(query)
            .onErrorReturn { listOf() }
            .flatMapPublisher { localTexts ->
                if (localTexts.isEmpty()) {
                    Flowable.error(IllegalStateException(NO_DATA_FROM_LOCAL_DB))
                } else {
                    Flowable.just(mapperToText(localTexts))
                }
            }
    }

    override fun insertText(textItem: TextItem): Single<Long> = textLocalDataSource.insertText(textItem.map())

    override fun deleteText(textItem: TextItem): Completable{

        return textLocalDataSource.delete(textItem.map())
    }

    override fun deleteAllTexts(): Completable = textLocalDataSource.deleteAllTexts()

}