package lilly.cleanarchitecture.domain.usecase.sharedpreference

import lilly.cleanarchitecture.domain.sharedpreference.StartRepository
import javax.inject.Inject

class GetInfoSkipUseCase @Inject constructor(private val repository: StartRepository) {
    fun execute(): Boolean = repository.infoSkip
}