package lilly.cleanarchitecture.ui.ble

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.polidea.rxandroidble2.exceptions.BleScanException
import dagger.hilt.android.AndroidEntryPoint
import lilly.cleanarchitecture.base.BaseActivity
import lilly.cleanarchitecture.utils.Util.Companion.repeatOnStarted
import lilly.cleanarchitecture.viewmodel.BleViewModel
import kotlinx.coroutines.flow.collect
import lilly.cleanarchitecture.R
import lilly.cleanarchitecture.databinding.ActivityBleBinding
import lilly.cleanarchitecture.utils.Util

@AndroidEntryPoint
class BleActivity: BaseActivity<ActivityBleBinding, BleViewModel>() {

    override val layoutResID: Int = R.layout.activity_ble
    override val viewModel by viewModels<BleViewModel>()
    private var requestEnableBluetooth = false


    companion object {
        const val REQUEST_LOCATION_PERMISSION = 1
        val LOCATION_PERMISSION = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun onResume() {
        super.onResume()
        if(viewModel.isConnected.value){
            binding.tvConnectedDeviceName.text = viewModel.getDeviceName()
        }
    }

    override fun initVariable() {
        binding.viewModel = viewModel
        if (!hasPermissions(this, LOCATION_PERMISSION)) {
            requestPermissions(LOCATION_PERMISSION, REQUEST_LOCATION_PERMISSION)
        }
    }
    override fun initView(){
        tabInit()
    }
    override fun initListener() {
        binding.apply{
            btnBleWrite.setOnClickListener {
                val writeDialog = WriteDialog(this@BleActivity, object : WriteDialog.WriteDialogListener {
                    override fun onClickSend(data: String, type: String) {
                        viewModel?.writeData(data, type)
                    }
                })
                writeDialog.show()
            }
        }
    }
    override fun initObserver() {
        repeatOnStarted {
            viewModel.eventFlow.collect{ event ->
                handleEvent(event)
            }
        }
        repeatOnStarted {
            viewModel.deviceConnectionEvent.collect { deviceEvent ->
                deviceEvent.run {
                    if (data) {
                        binding.tabLayoutBle.selectTab(binding.tabLayoutBle.getTabAt(1))
                        binding.tvConnectedDeviceName.text = deviceName
                        Util.showNotification("$deviceName Connected", "success")
                    } else {
                        Util.showNotification("$deviceName Disconnected", "error")
                    }
                }
            }
        }
    }
    private fun handleEvent(event: BleViewModel.Event) = when (event) {
        is BleViewModel.Event.BleScanException ->{
            bleThrowable(event.reason)
        }
        is BleViewModel.Event.ShowNotification->{
            Util.showNotification(event.message,event.type)
        }
        else->{}
    }

    private fun tabInit() {
        val scanFragment = ScanFragment()
        val readFragment = ReadFragment()
        supportFragmentManager.beginTransaction().add(R.id.tabcontent, scanFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.tabcontent, readFragment).commit()
        supportFragmentManager.beginTransaction().show(scanFragment).commit()
        supportFragmentManager.beginTransaction().hide(readFragment).commit()
        val view1: View = layoutInflater.inflate(R.layout.custom_tab, null)
        val tabText1 = view1.findViewById(R.id.custom_tab_text) as TextView
        tabText1.text = "Scan"
        view1.background = ContextCompat.getDrawable(applicationContext, R.drawable.tab_bg_selected)
        val view2: View = layoutInflater.inflate(R.layout.custom_tab, null)
        val tabText2 = view2.findViewById(R.id.custom_tab_text) as TextView
        view2.background = ContextCompat.getDrawable(
            applicationContext,
            R.drawable.tab_bg_unselected
        )
        tabText2.text = "Read"
        tabText2.setTextColor(ContextCompat.getColor(applicationContext,R.color.lilly_pink_typo))

        val tabs = binding.tabLayoutBle

        tabs.addTab(tabs.newTab().setCustomView(view1))
        tabs.addTab(tabs.newTab().setCustomView(view2))

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position

                tab.customView?.apply {
                    background = ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.tab_bg_selected
                    )
                    findViewById<TextView>(R.id.custom_tab_text).setTextColor(ContextCompat.getColor(applicationContext,R.color.lilly_blue_2))
                }

                when (position) {
                    0 -> {
                        supportFragmentManager.beginTransaction().show(scanFragment).commit()
                        supportFragmentManager.beginTransaction().hide(readFragment).commit()
                        if(!viewModel.isScanning.get()) viewModel.startScan()
                    }
                    1 -> {
                        viewModel.stopScan()
                        supportFragmentManager.beginTransaction().hide(scanFragment).commit()
                        supportFragmentManager.beginTransaction().show(readFragment).commit()
                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.customView?.apply {
                    background = ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.tab_bg_unselected
                    )
                    findViewById<TextView>(R.id.custom_tab_text).setTextColor(ContextCompat.getColor(applicationContext,R.color.lilly_pink_typo))
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    if(!viewModel.isScanning.get()) viewModel.startScan()
                }
            }
        })

        tabs.selectTab(tabs.getTabAt(0))
    }

    private fun bleThrowable(reason: Int) = when (reason) {
        BleScanException.BLUETOOTH_DISABLED -> {
            requestEnableBluetooth = true
            requestEnableBLE()
        }
        BleScanException.LOCATION_PERMISSION_MISSING->{
            requestPermissions(LOCATION_PERMISSION, REQUEST_LOCATION_PERMISSION)
        }
        else -> {
            Util.showNotification(bleScanExceptionReasonDescription(reason), "error")
        }
    }

    private fun bleScanExceptionReasonDescription(reason: Int): String {
        return when (reason) {
            BleScanException.BLUETOOTH_CANNOT_START -> "Bluetooth cannot start"
            BleScanException.BLUETOOTH_DISABLED -> "Bluetooth disabled"
            BleScanException.BLUETOOTH_NOT_AVAILABLE -> "Bluetooth not available"
            BleScanException.LOCATION_SERVICES_DISABLED -> "Location Services disabled"
            BleScanException.SCAN_FAILED_ALREADY_STARTED -> "Scan failed because it has already started"
            BleScanException.SCAN_FAILED_APPLICATION_REGISTRATION_FAILED -> "Scan failed because application registration failed"
            BleScanException.SCAN_FAILED_INTERNAL_ERROR -> "Scan failed because of an internal error"
            BleScanException.SCAN_FAILED_FEATURE_UNSUPPORTED -> "Scan failed because feature unsupported"
            BleScanException.SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES -> "Scan failed because out of hardware resources"
            BleScanException.UNDOCUMENTED_SCAN_THROTTLE -> "Undocumented scan throttle"
            BleScanException.UNKNOWN_ERROR_CODE -> "Unknown error"
            else -> "Unknown error"
        }
    }

    private val requestEnableBleResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                Util.showNotification("Bluetooth기능을 허용하였습니다.", "success")
                viewModel.startScan()
            } else {
                Util.showNotification("Bluetooth기능을 켜주세요.", "error")
                viewModel.startScan()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissions granted!", Toast.LENGTH_SHORT).show()
                } else {
                    //requestPermissions(permissions, LOCATION_PERMISSION)
                    Toast.makeText(this, "Permissions must be granted!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

}