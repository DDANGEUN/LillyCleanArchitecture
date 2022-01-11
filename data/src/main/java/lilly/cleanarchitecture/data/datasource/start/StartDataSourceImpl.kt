package lilly.cleanarchitecture.data.datasource.start

import lilly.cleanarchitecture.data.utils.PreferenceManager


class StartDataSourceImpl (private val preferenceManager: PreferenceManager) :
    StartDataSource {
    override var infoSkip: Boolean
        get() = preferenceManager.infoSkip
        set(value) {
            preferenceManager.infoSkip = value
        }
}