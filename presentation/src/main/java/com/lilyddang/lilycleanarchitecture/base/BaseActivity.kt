package com.lilyddang.lilycleanarchitecture.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<T: ViewDataBinding, R: ViewModel>: AppCompatActivity() {

    lateinit var binding: T
    abstract val viewModel: R
    abstract val layoutResID: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResID)
        binding.lifecycleOwner = this

        initVariable()
        initView()
        initObserver()
        initListener()
    }

    abstract fun initVariable()
    abstract fun initListener()
    abstract fun initObserver()
    abstract fun initView()


}