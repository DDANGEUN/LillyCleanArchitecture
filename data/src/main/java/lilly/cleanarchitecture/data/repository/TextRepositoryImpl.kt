package lilly.cleanarchitecture.data.repository

import lilly.cleanarchitecture.data.utils.NO_DATA_FROM_LOCAL_DB

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import lilly.cleanarchitecture.data.datasource.room.TextLocalDataSource
import lilly.cleanarchitecture.data.mapper.map
import lilly.cleanarchitecture.data.mapper.mapperToText
import lilly.cleanarchitecture.domain.room.TextRepository
import lilly.cleanarchitecture.domain.room.model.TextItem

class TextRepositoryImpl(private val textLocalDataSource: TextLocalDataSource): TextRepository {
    override fun getAllLocalTexts(): Flowable<List<TextItem>>
        = textLocalDataSource.getAllTexts().flatMap { localTexts-> Flowable.just(mapperToText(localTexts)) }

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
    override fun deleteText(textItem: TextItem): Completable = textLocalDataSource.delete(textItem.map())
    override fun deleteAllTexts(): Completable = textLocalDataSource.deleteAllTexts()

}