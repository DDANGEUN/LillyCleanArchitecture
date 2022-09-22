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

class Utils {
    companion object {
        /**
         * @param form - error, success, info, warning, normal, custom
         */
        const val NOTI_ERROR = "error"
        const val NOTI_SUCCESS= "success"
        const val NOTI_INFO = "info"
        const val NOTI_WARNING = "warning"
        const val NOTI_NORMAL = "normal"
        fun showNotification(msg: String, form: String) {
            when(form){
                NOTI_ERROR->{ Toasty.error(MyApplication.applicationContext(), msg, Toast.LENGTH_SHORT).show()}
                NOTI_SUCCESS->{ Toasty.success(MyApplication.applicationContext(), msg, Toast.LENGTH_SHORT).show()}
                NOTI_INFO->{ Toasty.info(MyApplication.applicationContext(), msg, Toast.LENGTH_SHORT).show()}
                NOTI_WARNING->{ Toasty.warning(MyApplication.applicationContext(), msg, Toast.LENGTH_SHORT).show()}
                NOTI_NORMAL->{ Toasty.normal(MyApplication.applicationContext(), msg, Toast.LENGTH_SHORT).show()}
            }

        }
        fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
            }
        }
    }
}