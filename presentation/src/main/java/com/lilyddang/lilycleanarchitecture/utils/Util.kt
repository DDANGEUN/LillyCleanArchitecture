package com.lilyddang.lilycleanarchitecture.utils

import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lilyddang.lilycleanarchitecture.MyApplication
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class Util {
    companion object {
        /**
         * @param form - error, success, info, warning, normal, custom
         */
        fun showNotification(msg: String, form: String) {
            when(form){
                "error"->{ Toasty.error(MyApplication.applicationContext(), msg, Toast.LENGTH_LONG).show()}
                "success"->{ Toasty.success(MyApplication.applicationContext(), msg, Toast.LENGTH_LONG).show()}
                "info"->{ Toasty.info(MyApplication.applicationContext(), msg, Toast.LENGTH_LONG).show()}
                "warning"->{ Toasty.warning(MyApplication.applicationContext(), msg, Toast.LENGTH_LONG).show()}
                "normal"->{ Toasty.normal(MyApplication.applicationContext(), msg, Toast.LENGTH_LONG).show()}
                //"custom" -> { showCustomNotification(msg)}
            }

        }
        fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
            }
        }
    }
}