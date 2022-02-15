package lilly.cleanarchitecture.domain.usecase.room

import io.reactivex.Flowable
import lilly.cleanarchitecture.domain.room.TextRepository
import lilly.cleanarchitecture.domain.room.model.TextItem

class GetSearchTextsUseCase(private val repository: TextRepository) {
    fun execute(query: String): Flowable<List<TextItem>> = repository.getLocalSearchTexts(query)
}