package lilly.cleanarchitecture.ui.main

import android.content.Intent
import lilly.cleanarchitecture.R
import lilly.cleanarchitecture.base.BaseActivity
import lilly.cleanarchitecture.databinding.ActivityStartBinding
import lilly.cleanarchitecture.viewmodel.StartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
class StartActivity : BaseActivity<ActivityStartBinding, StartViewModel>(){

    override val viewModel by viewModel<StartViewModel>()
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
        binding.apply {
            ivStartLily.clipToOutline = true
        }
    }

    override fun initObserver() {
    }

    override fun initListener() {
        binding.apply {
            btnStart.setOnClickListener {
                val intent = Intent(this@StartActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        }
    }

}