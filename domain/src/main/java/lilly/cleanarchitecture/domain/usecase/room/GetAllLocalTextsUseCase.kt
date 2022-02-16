package lilly.cleanarchitecture.domain.usecase.room

import io.reactivex.Flowable
import lilly.cleanarchitecture.domain.room.TextRepository
import lilly.cleanarchitecture.domain.room.model.TextItem
import javax.inject.Inject

class GetAllLocalTextsUseCase @Inject constructor(private val repository: TextRepository) {
    fun execute(): Flowable<List<TextItem>> = repository.getAllLocalTexts()
}