package com.lilyddang.lilycleanarchitecture.domain.usecase

import com.lilyddang.lilycleanarchitecture.domain.model.TextItem
import com.lilyddang.lilycleanarchitecture.domain.repository.TextRepository
import io.reactivex.Completable
import io.reactivex.Single

class InsertTextUseCase(private val repository: TextRepository) {
    fun execute(textItem: TextItem): Single<Long> = repository.insertText(textItem)
}