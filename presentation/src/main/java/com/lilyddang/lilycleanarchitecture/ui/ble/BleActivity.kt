package com.lilyddang.lilycleanarchitecture.ui.ble

import com.lilyddang.lilycleanarchitecture.R
import com.lilyddang.lilycleanarchitecture.base.BaseActivity
import com.lilyddang.lilycleanarchitecture.databinding.ActivityBleBinding
import com.lilyddang.lilycleanarchitecture.viewmodel.BleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BleActivity: BaseActivity<ActivityBleBinding, BleViewModel>() {

    override val layoutResID: Int = R.layout.activity_ble
    override val viewModel by viewModel<BleViewModel>()

    override fun initVariable() {
        binding.viewModel = viewModel
    }
    override fun initView(){ }
    override fun initListener() { }
    override fun initObserver() { }
}