package com.lilyddang.lilycleanarchitecture.viewmodel


import android.annotation.SuppressLint
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.lilyddang.lilycleanarchitecture.base.BaseViewModel
import com.lilyddang.lilycleanarchitecture.data.NO_DATA_FROM_LOCAL_DB
import com.lilyddang.lilycleanarchitecture.domain.model.TextItem
import com.lilyddang.lilycleanarchitecture.domain.usecase.DeleteTextUseCase
import com.lilyddang.lilycleanarchitecture.domain.usecase.GetAllLocalTextsUseCase
import com.lilyddang.lilycleanarchitecture.domain.usecase.GetSearchTextsUseCase
import com.lilyddang.lilycleanarchitecture.domain.usecase.InsertTextUseCase
import com.lilyddang.lilycleanarchitecture.utils.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RoomViewModel(
    private val getAllLocalTextsUseCase: GetAllLocalTextsUseCase,
    private val insertTextUseCase: InsertTextUseCase,
    private val deleteTextUseCase: DeleteTextUseCase,
    private val getSearchTextsUseCase: GetSearchTextsUseCase
) : BaseViewModel() {

    val statusText = ObservableField("Hi! Let's put text in Local DB")
    var textList: List<TextItem> = ArrayList()
    val textListObservable: MutableLiveData<List<TextItem>> = MutableLiveData(textList)
    val noDataNotification = ObservableBoolean(false)

    @SuppressLint("SimpleDateFormat")
    fun insertText(content: String) {
        val simpleDate = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val strNow: String = simpleDate.format(Date(System.currentTimeMillis()))
        CoroutineScope(Dispatchers.IO).launch {
            compositeDisposable.add(
                insertTextUseCase.execute(TextItem(null,strNow, content)).subscribe({ id->
                    statusText.set("$strNow `$content` is inserted.")
                    getAllTexts()
                }, {
                    CoroutineScope(Dispatchers.Main).launch {
                        Util.showNotification("error: ${it.message}", "error")
                    }
                })
            )
        }
    }

    fun deleteText(textItem: TextItem){
        CoroutineScope(Dispatchers.IO).launch {
            compositeDisposable.add(
                deleteTextUseCase.execute(textItem).subscribe({
                    statusText.set("`${textItem.content}` is deleted.")
                    getAllTexts()
                }, {
                    CoroutineScope(Dispatchers.Main).launch {
                        Util.showNotification("error: ${it.message}", "error")
                    }
                })
            )
        }
    }

    fun getAllTexts() {
        CoroutineScope(Dispatchers.IO).launch {
            compositeDisposable.add(
                getAllLocalTextsUseCase.execute().subscribe({
                    CoroutineScope(Dispatchers.Main).launch {
                        noDataNotification.set(false)
                        textList = it
                        textListObservable.value = textList
                    }
                }, {
                    CoroutineScope(Dispatchers.Main).launch {
                        if (it.message != NO_DATA_FROM_LOCAL_DB) {
                            Util.showNotification("error: ${it.message}", "error")
                        }else{
                            noDataNotification.set(true)
                        }
                    }
                })
            )
        }
    }

    fun getSearchTexts(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            compositeDisposable.add(
                getSearchTextsUseCase.execute(query).subscribe({
                    CoroutineScope(Dispatchers.Main).launch {
                        noDataNotification.set(false)
                        textList = it
                        textListObservable.value = textList
                    }
                }, {
                    CoroutineScope(Dispatchers.Main).launch {
                        if (it.message != NO_DATA_FROM_LOCAL_DB) {
                            Util.showNotification("error: ${it.message}", "error")
                        }else{
                            noDataNotification.set(true)
                        }

                    }
                })
            )
        }
    }
}