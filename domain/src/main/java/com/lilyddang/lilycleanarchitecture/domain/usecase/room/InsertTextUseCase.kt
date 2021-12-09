package com.lilyddang.lilycleanarchitecture.domain.usecase.room

import com.lilyddang.lilycleanarchitecture.domain.room.model.TextItem
import com.lilyddang.lilycleanarchitecture.domain.room.TextRepository
import io.reactivex.Single

class InsertTextUseCase(private val repository: TextRepository) {
    fun execute(textItem: TextItem): Single<Long> = repository.insertText(textItem)
}