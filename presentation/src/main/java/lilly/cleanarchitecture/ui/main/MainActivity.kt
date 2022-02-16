package lilly.cleanarchitecture.ui.main

import android.content.Intent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import lilly.cleanarchitecture.R
import lilly.cleanarchitecture.base.BaseActivity
import lilly.cleanarchitecture.databinding.ActivityMainBinding
import lilly.cleanarchitecture.ui.ble.BleActivity
import lilly.cleanarchitecture.ui.room.RoomActivity
import lilly.cleanarchitecture.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(){

    override val viewModel by viewModels<MainViewModel>()
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