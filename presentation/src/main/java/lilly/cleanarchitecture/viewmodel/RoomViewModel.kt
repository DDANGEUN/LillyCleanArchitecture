package lilly.cleanarchitecture.viewmodel


import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import lilly.cleanarchitecture.data.utils.NO_DATA_FROM_LOCAL_DB
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import lilly.cleanarchitecture.base.BaseViewModel
import lilly.cleanarchitecture.domain.room.model.TextItem
import lilly.cleanarchitecture.domain.usecase.room.DeleteTextUseCase
import lilly.cleanarchitecture.domain.usecase.room.GetAllLocalTextsUseCase
import lilly.cleanarchitecture.domain.usecase.room.GetSearchTextsUseCase
import lilly.cleanarchitecture.domain.usecase.room.InsertTextUseCase
import lilly.cleanarchitecture.utils.Util
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class RoomViewModel @Inject constructor(
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
        addDisposable(
            insertTextUseCase.execute(TextItem(null, strNow, content))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ id ->
                    statusText.set("$strNow `$content` is inserted.")
                }, {
                    event(Event.ShowNotification("${it.message}", "error"))
                })
        )

    }

    fun deleteText(textItem: TextItem) = addDisposable(
        deleteTextUseCase.execute(textItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                statusText.set("`${textItem.content}` is deleted.")
            }, {
                Util.showNotification("error: ${it.message}", "error")
            })
    )


    private fun getAllTexts() = addDisposable(
        getAllLocalTextsUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                noDataNotification.set(it.isEmpty())
                textListObservable.value = it
            }, {
            })
    )


    fun getSearchTexts(query: String) = addDisposable(
        getSearchTextsUseCase.execute(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                noDataNotification.set(false)
                textListObservable.value = it
            }, {
                if (it.message == NO_DATA_FROM_LOCAL_DB) {
                    noDataNotification.set(true)
                } else {
                    event(Event.ShowNotification("${it.message}", "error"))
                }
            })
    )

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()
    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        data class ShowNotification(val msg: String, val type: String) : Event()
    }
}