package com.lilyddang.lilycleanarchitecture.domain.usecase.room

import com.lilyddang.lilycleanarchitecture.domain.room.model.TextItem
import com.lilyddang.lilycleanarchitecture.domain.room.TextRepository
import io.reactivex.Flowable

class GetAllLocalTextsUseCase(private val repository: TextRepository) {
    fun execute(): Flowable<List<TextItem>> = repository.getAllLocalTexts()
}