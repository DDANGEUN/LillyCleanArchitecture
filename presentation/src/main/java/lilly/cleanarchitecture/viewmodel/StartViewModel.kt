package lilly.cleanarchitecture.viewmodel

import android.widget.CompoundButton
import lilly.cleanarchitecture.base.BaseViewModel
import lilly.cleanarchitecture.domain.usecase.sharedpreference.GetInfoSkipUseCase
import lilly.cleanarchitecture.domain.usecase.sharedpreference.InsertInfoSkipUseCase

class StartViewModel(
    private val insertInfoSkipUseCase: InsertInfoSkipUseCase,
    private val getInfoSkipUseCase: GetInfoSkipUseCase
): BaseViewModel() {

    val startSkip = getInfoSkipUseCase.execute()

    fun skipCheckChanged(button: CompoundButton, check: Boolean){
        insertInfoSkipUseCase.execute(check)
    }
}