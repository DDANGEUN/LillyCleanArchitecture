package com.lilyddang.lilycleanarchitecture.domain.usecase.room

import com.lilyddang.lilycleanarchitecture.domain.room.model.TextItem
import com.lilyddang.lilycleanarchitecture.domain.room.TextRepository
import io.reactivex.Completable

class DeleteTextUseCase(private val repository: TextRepository) {
    fun execute(textItem: TextItem): Completable = repository.deleteText(textItem)
}