package lilly.cleanarchitecture.domain.usecase.room

import lilly.cleanarchitecture.domain.room.TextRepository
import lilly.cleanarchitecture.domain.room.model.TextItem
import javax.inject.Inject

class InsertTextUseCase @Inject constructor(private val repository: TextRepository) {
    fun execute(textItem: TextItem): Long = repository.insertText(textItem)
}