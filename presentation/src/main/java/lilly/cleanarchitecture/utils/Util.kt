package lilly.cleanarchitecture.utils

import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import lilly.cleanarchitecture.MyApplication

class Util {
    companion object {
        /**
         * @param form - error, success, info, warning, normal, custom
         */
        fun showNotification(msg: String, form: String) {
            when(form){
                "error"->{ Toasty.error(MyApplication.applicationContext(), msg, Toast.LENGTH_SHORT).show()}
                "success"->{ Toasty.success(MyApplication.applicationContext(), msg, Toast.LENGTH_SHORT).show()}
                "info"->{ Toasty.info(MyApplication.applicationContext(), msg, Toast.LENGTH_SHORT).show()}
                "warning"->{ Toasty.warning(MyApplication.applicationContext(), msg, Toast.LENGTH_SHORT).show()}
                "normal"->{ Toasty.normal(MyApplication.applicationContext(), msg, Toast.LENGTH_SHORT).show()}
            }

        }
        fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
            }
        }
    }
}