package lilly.cleanarchitecture.domain.usecase.room

import io.reactivex.Single
import lilly.cleanarchitecture.domain.room.TextRepository
import lilly.cleanarchitecture.domain.room.model.TextItem
import javax.inject.Inject

class InsertTextUseCase @Inject constructor(private val repository: TextRepository) {
    fun execute(textItem: TextItem): Single<Long> = repository.insertText(textItem)
}