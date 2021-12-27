package com.lilyddang.lilycleanarchitecture.ui.ble

import com.lilyddang.lilycleanarchitecture.R
import com.lilyddang.lilycleanarchitecture.base.BaseFragment
import com.lilyddang.lilycleanarchitecture.databinding.FragmentReadBinding
import com.lilyddang.lilycleanarchitecture.viewmodel.BleViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ReadFragment : BaseFragment<FragmentReadBinding, BleViewModel>() {

    override val viewModel by sharedViewModel<BleViewModel>()
    override val layoutResID: Int = R.layout.fragment_read
    override fun initVariable() {
        binding.viewModel = viewModel
    }
    override fun initView() {
    }
    override fun initListener() {
    }
    override fun initObserver() {
    }
}