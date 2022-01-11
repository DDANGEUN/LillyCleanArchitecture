package lilly.cleanarchitecture.domain.usecase.sharedpreference

import lilly.cleanarchitecture.domain.sharedpreference.StartRepository

class GetInfoSkipUseCase(private val repository: StartRepository) {
    fun execute(): Boolean = repository.infoSkip
}