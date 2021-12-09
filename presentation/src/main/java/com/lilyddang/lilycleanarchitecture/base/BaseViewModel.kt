package com.lilyddang.lilycleanarchitecture.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BaseViewModel : ViewModel() {
    private val disposable = CompositeDisposable()
    protected fun addDisposable(d: Disposable) {
        disposable.add(d)
    }
    override fun onCleared() {
        if (!disposable.isDisposed) {
            disposable.clear()
        }
        super.onCleared()
    }
}