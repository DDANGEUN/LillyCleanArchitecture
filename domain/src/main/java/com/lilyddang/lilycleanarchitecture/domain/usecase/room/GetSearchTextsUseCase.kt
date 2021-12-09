package com.lilyddang.lilycleanarchitecture.domain.usecase.room

import com.lilyddang.lilycleanarchitecture.domain.room.model.TextItem
import com.lilyddang.lilycleanarchitecture.domain.room.TextRepository
import io.reactivex.Flowable

class GetSearchTextsUseCase(private val repository: TextRepository) {
    fun execute(query: String): Flowable<List<TextItem>> = repository.getLocalSearchTexts(query)
}