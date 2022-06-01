package lilly.cleanarchitecture.domain.usecase.room

import kotlinx.coroutines.flow.Flow
import lilly.cleanarchitecture.domain.room.TextRepository
import lilly.cleanarchitecture.domain.room.model.TextItem
import javax.inject.Inject

class GetSearchTextsUseCase @Inject constructor(private val repository: TextRepository) {
    fun execute(query: String): Flow<List<TextItem>> = repository.getLocalSearchTexts(query)
}