package lilly.cleanarchitecture.viewmodel


import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import lilly.cleanarchitecture.base.BaseViewModel
import lilly.cleanarchitecture.domain.room.model.TextItem
import lilly.cleanarchitecture.domain.usecase.room.DeleteTextUseCase
import lilly.cleanarchitecture.domain.usecase.room.GetAllLocalTextsUseCase
import lilly.cleanarchitecture.domain.usecase.room.GetSearchTextsUseCase
import lilly.cleanarchitecture.domain.usecase.room.InsertTextUseCase
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val getAllLocalTextsUseCase: GetAllLocalTextsUseCase,
    private val insertTextUseCase: InsertTextUseCase,
    private val deleteTextUseCase: DeleteTextUseCase,
    private val getSearchTextsUseCase: GetSearchTextsUseCase
) : BaseViewModel() {

    val statusText = ObservableField("Hi! Let's put text in Local DB")

    val noDataNotification = ObservableBoolean(false)


    @SuppressLint("SimpleDateFormat")
    fun insertText(content: String) {
        val simpleDate = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val strNow: String = simpleDate.format(Date(System.currentTimeMillis()))
        CoroutineScope(IO).launch {
            insertTextUseCase.execute(TextItem(null, strNow, content))
        }
        statusText.set("$strNow `$content` is inserted.")
    }

    fun deleteText(textItem: TextItem) = CoroutineScope(IO).launch {
        deleteTextUseCase.execute(textItem)
    }


    fun getAllTexts() = getAllLocalTextsUseCase.execute()
        .flowOn(IO)
        .catch { e: Throwable -> event(Event.ShowNotification("${e.message}", "error")) }


    fun getSearchTexts(query: String) = getSearchTextsUseCase.execute(query)
        .flowOn(IO)
        .catch { e: Throwable -> event(Event.ShowNotification("${e.message}", "error")) }


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