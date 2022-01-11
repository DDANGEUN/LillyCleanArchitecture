package lilly.cleanarchitecture.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import lilly.cleanarchitecture.R
import java.util.*

class SimpleDialog(mContext: Context, private val title:String, simpleDialogListener: SimpleDialogListener): Dialog(mContext) {

    private var simpleDialogListener: SimpleDialogListener? = simpleDialogListener

    interface SimpleDialogListener {
        fun onClickOkButton()
        fun onClickCancleButton()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_simple)

        Objects.requireNonNull(window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        val title = findViewById<TextView>(R.id.tv_simpledialog)
        title.text = this.title

        val btnCancle = findViewById<Button>(R.id.btn_simpledialog_cancel)
        val btnOk = findViewById<Button>(R.id.btn_simpledialog_ok)

        btnCancle.setOnClickListener {
            simpleDialogListener?.onClickCancleButton()
            dismiss()
        }
        btnOk.setOnClickListener {
            simpleDialogListener?.onClickOkButton()
            dismiss()
        }


    }
}