package lilly.cleanarchitecture.domain.usecase.sharedpreference

import lilly.cleanarchitecture.domain.sharedpreference.StartRepository

class InsertInfoSkipUseCase(private val repository: StartRepository) {
    fun execute(skip: Boolean) {
        repository.infoSkip = skip
    }
}