package lilly.cleanarchitecture.domain.usecase.room

import io.reactivex.Single
import lilly.cleanarchitecture.domain.room.TextRepository
import lilly.cleanarchitecture.domain.room.model.TextItem

class InsertTextUseCase(private val repository: TextRepository) {
    fun execute(textItem: TextItem): Single<Long> = repository.insertText(textItem)
}