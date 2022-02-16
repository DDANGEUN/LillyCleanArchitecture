package lilly.cleanarchitecture.domain.usecase.room

import io.reactivex.Flowable
import lilly.cleanarchitecture.domain.room.TextRepository
import lilly.cleanarchitecture.domain.room.model.TextItem
import javax.inject.Inject

class GetSearchTextsUseCase @Inject constructor(private val repository: TextRepository) {
    fun execute(query: String): Flowable<List<TextItem>> = repository.getLocalSearchTexts(query)
}