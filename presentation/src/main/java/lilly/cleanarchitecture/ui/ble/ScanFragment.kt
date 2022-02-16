package lilly.cleanarchitecture.ui.ble

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.polidea.rxandroidble2.scan.ScanResult
import dagger.hilt.android.AndroidEntryPoint
import lilly.cleanarchitecture.base.BaseFragment
import lilly.cleanarchitecture.utils.Util.Companion.repeatOnStarted
import lilly.cleanarchitecture.viewmodel.BleViewModel
import kotlinx.coroutines.flow.collect
import lilly.cleanarchitecture.R
import lilly.cleanarchitecture.databinding.FragmentScanBinding

@AndroidEntryPoint
class ScanFragment : BaseFragment<FragmentScanBinding, BleViewModel>() {

    override val viewModel by activityViewModels<BleViewModel>()
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
                viewModel.connectDevice(scanResult.bleDevice)
            }
        })
    }

    override fun initObserver() {
        repeatOnStarted {
            viewModel.eventFlow.collect{ event ->
                handleEvent(event)
            }
        }
    }

    private fun handleEvent(event: BleViewModel.Event) = when (event) {
        is BleViewModel.Event.ListUpdate->{
            scanListAdapter.setItem(event.results)
        }
        else->{}
    }
}