package com.lilyddang.lilycleanarchitecture.domain.usecase

import com.lilyddang.lilycleanarchitecture.domain.model.TextItem
import com.lilyddang.lilycleanarchitecture.domain.repository.TextRepository
import io.reactivex.Flowable

class GetAllLocalTextsUseCase(private val repository: TextRepository) {
    fun execute(): Flowable<List<TextItem>> = repository.getAllLocalTexts()
}