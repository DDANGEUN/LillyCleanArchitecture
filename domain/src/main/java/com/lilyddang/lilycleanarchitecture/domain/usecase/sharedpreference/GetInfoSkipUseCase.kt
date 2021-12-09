package com.lilyddang.lilycleanarchitecture.domain.usecase.sharedpreference

import com.lilyddang.lilycleanarchitecture.domain.sharedpreference.StartRepository

class GetInfoSkipUseCase(private val repository: StartRepository) {
    fun execute(): Boolean = repository.infoSkip
}