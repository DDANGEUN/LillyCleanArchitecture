package com.lilyddang.lilycleanarchitecture.ui.ble

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lilyddang.lilycleanarchitecture.R
import com.lilyddang.lilycleanarchitecture.base.BaseFragment
import com.lilyddang.lilycleanarchitecture.databinding.FragmentScanBinding
import com.lilyddang.lilycleanarchitecture.utils.Util
import com.lilyddang.lilycleanarchitecture.utils.Util.Companion.repeatOnStarted
import com.lilyddang.lilycleanarchitecture.viewmodel.BleViewModel
import com.polidea.rxandroidble2.scan.ScanResult
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ScanFragment : BaseFragment<FragmentScanBinding, BleViewModel>() {

    override val viewModel by sharedViewModel<BleViewModel>()
    override val layoutResID: Int = R.layout.fragment_scan
    private lateinit var scanListAdapter: ScanListAdapter
    override fun initVariable() {
        binding.viewModel = viewModel
        scanListAdapter = ScanListAdapter()
    }
    override fun initView() {
        binding.apply {
            rvBleScanlist.apply {
                setHasFixedSize(true)
                val layoutManager1: RecyclerView.LayoutManager =
                    LinearLayoutManager(activity)
                layoutManager = layoutManager1
                adapter = scanListAdapter
            }
        }
    }
    override fun initListener() {
        scanListAdapter.setItemClickListener(object : ScanListAdapter.ItemClickListener {
            override fun onClick(view: View, scanResult: ScanResult) {
                // TODO
            }
        })
    }

    override fun initObserver() {
        repeatOnStarted {
            viewModel.eventFlow.collect{ event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: BleViewModel.Event) = when (event) {
        is BleViewModel.Event.ListUpdate->{
            scanListAdapter.setItem(event.reults)
        }
        else->{}
    }
}