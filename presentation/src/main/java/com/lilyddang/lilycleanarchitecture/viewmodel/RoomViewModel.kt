package com.lilyddang.lilycleanarchitecture.viewmodel


import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.lilyddang.lilycleanarchitecture.base.BaseViewModel
import com.lilyddang.lilycleanarchitecture.data.NO_DATA_FROM_LOCAL_DB
import com.lilyddang.lilycleanarchitecture.domain.room.model.TextItem
import com.lilyddang.lilycleanarchitecture.domain.usecase.room.DeleteTextUseCase
import com.lilyddang.lilycleanarchitecture.domain.usecase.room.GetAllLocalTextsUseCase
import com.lilyddang.lilycleanarchitecture.domain.usecase.room.GetSearchTextsUseCase
import com.lilyddang.lilycleanarchitecture.domain.usecase.room.InsertTextUseCase
import com.lilyddang.lilycleanarchitecture.utils.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
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
    val textListObservable: MutableStateFlow<List<TextItem>> = MutableStateFlow(ArrayList())
    val noDataNotification = ObservableBoolean(false)

    init {
        getAllTexts()
    }


    @SuppressLint("SimpleDateFormat")
    fun insertText(content: String) {
        val simpleDate = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val strNow: String = simpleDate.format(Date(System.currentTimeMillis()))
        CoroutineScope(Dispatchers.IO).launch {
            addDisposable(
                insertTextUseCase.execute(TextItem(null, strNow, content))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ id ->
                        statusText.set("$strNow `$content` is inserted.")
                    }, {
                        Util.showNotification("error: ${it.message}", "error")
                    })
            )
        }
    }

    fun deleteText(textItem: TextItem) = CoroutineScope(Dispatchers.IO).launch {
        addDisposable(
            deleteTextUseCase.execute(textItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    statusText.set("`${textItem.content}` is deleted.")
                }, {
                    Util.showNotification("error: ${it.message}", "error")
                })
        )

    }

    private fun getAllTexts() = CoroutineScope(Dispatchers.IO).launch {
        addDisposable(
            getAllLocalTextsUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    noDataNotification.set(it.isEmpty())
                    textListObservable.value = it
                }, {
                })
        )
    }


    fun getSearchTexts(query: String) = CoroutineScope(Dispatchers.IO).launch {
        addDisposable(
            getSearchTextsUseCase.execute(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    noDataNotification.set(false)
                    textListObservable.value = it
                }, {
                    if (it.message != NO_DATA_FROM_LOCAL_DB) {
                        Util.showNotification("error: ${it.message}", "error")
                    } else {
                        noDataNotification.set(true)
                    }

                })
        )

    }
}