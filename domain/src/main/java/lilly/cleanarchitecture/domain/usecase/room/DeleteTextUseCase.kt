package lilly.cleanarchitecture.domain.usecase.room

import io.reactivex.Completable
import lilly.cleanarchitecture.domain.room.TextRepository
import lilly.cleanarchitecture.domain.room.model.TextItem

class DeleteTextUseCase(private val repository: TextRepository) {
    fun execute(textItem: TextItem): Completable = repository.deleteText(textItem)
}