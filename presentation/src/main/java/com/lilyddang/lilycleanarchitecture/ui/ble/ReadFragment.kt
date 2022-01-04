package com.lilyddang.lilycleanarchitecture.ui.ble

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lilyddang.lilycleanarchitecture.R
import com.lilyddang.lilycleanarchitecture.base.BaseFragment
import com.lilyddang.lilycleanarchitecture.databinding.FragmentReadBinding
import com.lilyddang.lilycleanarchitecture.utils.Util
import com.lilyddang.lilycleanarchitecture.utils.Util.Companion.repeatOnStarted
import com.lilyddang.lilycleanarchitecture.viewmodel.BleViewModel
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat
import java.util.*

class ReadFragment : BaseFragment<FragmentReadBinding, BleViewModel>() {

    override val viewModel by sharedViewModel<BleViewModel>()
    override val layoutResID: Int = R.layout.fragment_read
    private var readDataAdapter: ReadDataAdapter? = null
    private var readItem = ArrayList<Pair<String,String>>()
    override fun initVariable() {
        binding.viewModel = viewModel
    }
    override fun initView() {
        // recycler view
        val layoutManager1: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        readDataAdapter = ReadDataAdapter()
        binding.rvBleRead.apply {
            setHasFixedSize(true)
            this.layoutManager = layoutManager1
            adapter = readDataAdapter
        }
    }
    override fun initListener() {
    }
    override fun initObserver() {
        repeatOnStarted {
            viewModel.eventFlow.collect{ event ->
                handleEvent(event)
            }
        }
    }
    private fun handleEvent(event: BleViewModel.Event) = when(event){
        is BleViewModel.Event.ReadLogUpdate ->{
            val now = System.currentTimeMillis()
            val datef = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
            val timestamp = datef.format(Date(now))

            var read = event.hexString

            if (read.contains('\n')) {
                if (read.indexOf('\n') == read.lastIndex) {
                    read = read.substring(0, read.length - 1)
                }
            }

            readItem.add(Pair(timestamp,read))
            readDataAdapter?.setItem(readItem)
            binding.rvBleRead.scrollToPosition(readDataAdapter!!.itemCount - 1)
        }
        else -> {}
    }


}