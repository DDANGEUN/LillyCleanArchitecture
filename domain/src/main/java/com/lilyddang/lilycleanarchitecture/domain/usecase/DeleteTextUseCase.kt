package com.lilyddang.lilycleanarchitecture.domain.usecase

import com.lilyddang.lilycleanarchitecture.domain.model.TextItem
import com.lilyddang.lilycleanarchitecture.domain.repository.TextRepository
import io.reactivex.Completable

class DeleteTextUseCase(private val repository: TextRepository) {
    fun execute(textItem: TextItem): Completable = repository.deleteText(textItem)
}