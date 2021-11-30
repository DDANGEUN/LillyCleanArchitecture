package com.lilyddang.lilycleanarchitecture.domain.usecase

import com.lilyddang.lilycleanarchitecture.domain.repository.StartRepository

class InsertInfoSkipUseCase(private val repository: StartRepository) {
    fun execute(skip: Boolean) {
        repository.infoSkip = skip
    }
}