package com.lilyddang.lilycleanarchitecture.ui

import android.content.Intent
import com.lilyddang.lilycleanarchitecture.R
import com.lilyddang.lilycleanarchitecture.base.BaseActivity
import com.lilyddang.lilycleanarchitecture.databinding.ActivityMainBinding
import com.lilyddang.lilycleanarchitecture.ui.ble.BleActivity
import com.lilyddang.lilycleanarchitecture.ui.room.RoomActivity
import com.lilyddang.lilycleanarchitecture.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(){

    override val viewModel by viewModel<MainViewModel>()
    override val layoutResID: Int = R.layout.activity_main

    override fun initVariable() {
        binding.viewModel = viewModel
    }

    override fun initView() {
    }

    override fun initObserver() {
    }

    override fun initListener() {
        binding.apply{
            btnRoom.setOnClickListener {
                val intent = Intent(this@MainActivity, RoomActivity::class.java)
                startActivity(intent)

            }
            btnBle.setOnClickListener {
                val intent = Intent(this@MainActivity, BleActivity::class.java)
                startActivity(intent)
            }
        }
    }

}