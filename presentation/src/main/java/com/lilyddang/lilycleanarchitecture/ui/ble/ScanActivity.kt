package com.lilyddang.lilycleanarchitecture.ui.ble

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lilyddang.lilycleanarchitecture.R
import com.lilyddang.lilycleanarchitecture.base.BaseActivity
import com.lilyddang.lilycleanarchitecture.databinding.ActivityScanBinding
import com.lilyddang.lilycleanarchitecture.utils.Util
import com.lilyddang.lilycleanarchitecture.utils.Util.Companion.repeatOnStarted
import com.lilyddang.lilycleanarchitecture.viewmodel.ScanViewModel
import com.polidea.rxandroidble2.scan.ScanResult
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.collect

class ScanActivity: BaseActivity<ActivityScanBinding, ScanViewModel>() {

    override val layoutResID: Int = R.layout.activity_scan
    override val viewModel by viewModel<ScanViewModel>()
    private lateinit var scanListAdapter: ScanListAdapter
    private var requestEnableBluetooth = false
    private var askGrant = false

    companion object {
        const val REQUEST_ALL_PERMISSION = 1
        val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun initVariable() {
        binding.viewModel = viewModel
        if (!hasPermissions(this, PERMISSIONS)) {
            requestPermissions(PERMISSIONS, REQUEST_ALL_PERMISSION)
        }
        scanListAdapter = ScanListAdapter()
    }
    override fun initView(){
        binding.apply {
            rvBleScanlist.apply {
                setHasFixedSize(true)
                val layoutManager1: RecyclerView.LayoutManager =
                    LinearLayoutManager(this@ScanActivity)
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
        binding.apply{
            btnBleScan.setOnClickListener {
                viewModel?.startScan()
            }
        }
    }
    override fun initObserver() {
        repeatOnStarted {viewModel.apply {
            eventFlow.collect{ event -> handleEvent(event) }
        } }
    }
    private fun handleEvent(event: ScanViewModel.Event) = when (event) {
        is ScanViewModel.Event.BleScanException ->{
            requestEnableBluetooth = true
            requestEnableBLE()
        }
        is ScanViewModel.Event.ListUpdate->{
            scanListAdapter.setItem(event.reults)
        }
        is ScanViewModel.Event.ShowNotification->{
            Util.showNotification(event.message,event.type)
        }
    }
    private val requestEnableBleResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                Util.showNotification("Bluetooth기능을 허용하였습니다.", "success")
            } else {
                Util.showNotification("Bluetooth기능을 켜주세요.", "error")
            }
            requestEnableBluetooth = false
        }
    private fun requestEnableBLE() {
        val bleEnableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        requestEnableBleResult.launch(bleEnableIntent)
    }


    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }
    // Permission check
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ALL_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissions granted!", Toast.LENGTH_SHORT).show()
                } else {
                    requestPermissions(permissions, REQUEST_ALL_PERMISSION)
                    Toast.makeText(this, "Permissions must be granted", Toast.LENGTH_SHORT).show()
                    askGrant = false
                }
            }
        }
    }

}