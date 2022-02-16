package lilly.cleanarchitecture.viewmodel

import android.widget.CompoundButton
import dagger.hilt.android.lifecycle.HiltViewModel
import lilly.cleanarchitecture.base.BaseViewModel
import lilly.cleanarchitecture.domain.usecase.sharedpreference.GetInfoSkipUseCase
import lilly.cleanarchitecture.domain.usecase.sharedpreference.InsertInfoSkipUseCase
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val insertInfoSkipUseCase: InsertInfoSkipUseCase,
    getInfoSkipUseCase: GetInfoSkipUseCase
): BaseViewModel() {

    val startSkip = getInfoSkipUseCase.execute()

    fun skipCheckChanged(button: CompoundButton, check: Boolean){
        insertInfoSkipUseCase.execute(check)
    }
}