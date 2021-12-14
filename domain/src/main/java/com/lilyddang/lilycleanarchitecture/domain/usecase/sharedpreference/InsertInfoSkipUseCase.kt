package com.lilyddang.lilycleanarchitecture.domain.usecase.sharedpreference

import com.lilyddang.lilycleanarchitecture.domain.sharedpreference.StartRepository

class InsertInfoSkipUseCase(private val repository: StartRepository) {
    fun execute(skip: Boolean) {
        repository.infoSkip = skip
    }
}