package lilly.cleanarchitecture.ui.ble

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import lilly.cleanarchitecture.R
import java.util.*

class WriteDialog(val mContext: Context, writeDialogListener: WriteDialogListener) : Dialog(mContext) {


    private var writeDialogListener: WriteDialogListener? = writeDialogListener

    interface WriteDialogListener {
        fun onClickSend(data: String, type: String)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_write)


        Objects.requireNonNull(window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val etText: EditText = findViewById(R.id.text_write)
        val saveButton: Button = findViewById(R.id.btnSave)
        val cancelButton: Button = findViewById(R.id.btnCancle)
        val typeString: RadioButton = findViewById(R.id.type_string)
        val typeByte: RadioButton = findViewById(R.id.type_byte)
        typeString.isChecked = true
        // 버튼 리스너 설정
        saveButton.setOnClickListener {
            val dataText = etText.text.toString()

            val type = if(typeString.isChecked) "string" else "byte"

            if(dataText.isEmpty()) {
                Toast.makeText(mContext,"Please enter the data.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(type=="byte" && dataText.length<2){
                Toast.makeText(mContext,"Please enter the data like 00 (more than 2 digits.)",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            writeDialogListener?.onClickSend(dataText, type)
            dismiss()
        }
        cancelButton.setOnClickListener{
            dismiss()
        }
    }

}