package com.lilyddang.lilycleanarchitecture.data.repository

import com.lilyddang.lilycleanarchitecture.data.datasource.start.StartDataSource
import com.lilyddang.lilycleanarchitecture.domain.sharedpreference.StartRepository

class StartRepositoryImpl(private val startDataSource: StartDataSource) :
    StartRepository {
    override var infoSkip: Boolean
        get() = startDataSource.infoSkip
        set(value) {
            startDataSource.infoSkip = value
        }
}