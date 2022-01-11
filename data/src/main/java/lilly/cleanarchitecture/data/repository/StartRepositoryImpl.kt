package lilly.cleanarchitecture.data.repository

import lilly.cleanarchitecture.data.datasource.start.StartDataSource
import lilly.cleanarchitecture.domain.sharedpreference.StartRepository

class StartRepositoryImpl(private val startDataSource: StartDataSource) :
    StartRepository {
    override var infoSkip: Boolean
        get() = startDataSource.infoSkip
        set(value) {
            startDataSource.infoSkip = value
        }
}