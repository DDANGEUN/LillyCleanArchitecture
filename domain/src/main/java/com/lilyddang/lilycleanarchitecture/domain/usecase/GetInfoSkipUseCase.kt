package com.lilyddang.lilycleanarchitecture.domain.usecase

import com.lilyddang.lilycleanarchitecture.domain.repository.StartRepository

class GetInfoSkipUseCase(private val repository: StartRepository) {
    fun execute(): Boolean = repository.infoSkip
}