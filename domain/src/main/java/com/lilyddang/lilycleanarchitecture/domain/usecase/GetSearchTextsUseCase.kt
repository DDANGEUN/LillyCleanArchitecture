package com.lilyddang.lilycleanarchitecture.domain.usecase

import com.lilyddang.lilycleanarchitecture.domain.model.TextItem
import com.lilyddang.lilycleanarchitecture.domain.repository.TextRepository
import io.reactivex.Flowable

class GetSearchTextsUseCase(private val repository: TextRepository) {
    fun execute(query: String): Flowable<List<TextItem>> = repository.getLocalSearchTexts(query)
}