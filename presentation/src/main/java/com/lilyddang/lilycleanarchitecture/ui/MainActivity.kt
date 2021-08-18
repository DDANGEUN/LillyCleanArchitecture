package com.lilyddang.lilycleanarchitecture.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.lilyddang.lilycleanarchitecture.R
import com.lilyddang.lilycleanarchitecture.databinding.ActivityMainBinding
import com.lilyddang.lilycleanarchitecture.ui.ble.BleActivity
import com.lilyddang.lilycleanarchitecture.ui.room.RoomActivity
import com.lilyddang.lilycleanarchitecture.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val viewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply{
            viewModel = viewModel
            lifecycleOwner = this@MainActivity
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