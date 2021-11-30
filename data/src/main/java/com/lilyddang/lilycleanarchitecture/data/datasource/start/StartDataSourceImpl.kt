package com.lilyddang.lilycleanarchitecture.data.datasource.start

import com.lilyddang.lilycleanarchitecture.data.utils.PreferenceManager


class StartDataSourceImpl (private val preferenceManager: PreferenceManager) :
    StartDataSource {
    override var infoSkip: Boolean
        get() = preferenceManager.infoSkip
        set(value) {
            preferenceManager.infoSkip = value
        }
}