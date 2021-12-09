package com.lilyddang.lilycleanarchitecture.viewmodel

import android.widget.CompoundButton
import com.lilyddang.lilycleanarchitecture.base.BaseViewModel
import com.lilyddang.lilycleanarchitecture.domain.usecase.sharedpreference.GetInfoSkipUseCase
import com.lilyddang.lilycleanarchitecture.domain.usecase.sharedpreference.InsertInfoSkipUseCase

class StartViewModel(
    private val insertInfoSkipUseCase: InsertInfoSkipUseCase,
    private val getInfoSkipUseCase: GetInfoSkipUseCase
): BaseViewModel() {

    val startSkip = getInfoSkipUseCase.execute()

    fun skipCheckChanged(button: CompoundButton, check: Boolean){
        insertInfoSkipUseCase.execute(check)
    }
}