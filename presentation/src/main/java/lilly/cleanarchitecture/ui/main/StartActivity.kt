package lilly.cleanarchitecture.ui.main

import android.content.Intent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import lilly.cleanarchitecture.R
import lilly.cleanarchitecture.base.BaseActivity
import lilly.cleanarchitecture.databinding.ActivityStartBinding
import lilly.cleanarchitecture.viewmodel.StartViewModel


@AndroidEntryPoint
class StartActivity : BaseActivity<ActivityStartBinding, StartViewModel>(){

    override val viewModel by viewModels<StartViewModel>()
    override val layoutResID: Int = R.layout.activity_start

    override fun initVariable() {
        binding.viewModel = viewModel
    }

    override fun initView() {
        if(viewModel.startSkip) {
            val intent = Intent(this@StartActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        with(binding){
            ivStartLily.clipToOutline = true
        }
    }

    override fun initObserver() {
    }

    override fun initListener() {
        with(binding){
            btnStart.setOnClickListener {
                val intent = Intent(this@StartActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        }
    }

}