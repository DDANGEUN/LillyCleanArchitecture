package lilly.cleanarchitecture.domain.usecase.sharedpreference

import lilly.cleanarchitecture.domain.sharedpreference.StartRepository
import javax.inject.Inject

class InsertInfoSkipUseCase @Inject constructor(private val repository: StartRepository) {
    fun execute(skip: Boolean) {
        repository.infoSkip = skip
    }
}