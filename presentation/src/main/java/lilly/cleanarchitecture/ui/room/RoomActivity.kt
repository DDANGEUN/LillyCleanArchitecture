package lilly.cleanarchitecture.ui.room

import android.graphics.*
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import lilly.cleanarchitecture.base.BaseActivity
import lilly.cleanarchitecture.utils.Utils.Companion.repeatOnStarted
import lilly.cleanarchitecture.viewmodel.RoomViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import lilly.cleanarchitecture.R
import lilly.cleanarchitecture.databinding.ActivityRoomBinding
import lilly.cleanarchitecture.utils.Utils

@AndroidEntryPoint
class RoomActivity : BaseActivity<ActivityRoomBinding, RoomViewModel>() {

    override val layoutResID: Int = R.layout.activity_room
    override val viewModel by viewModels<RoomViewModel>()
    private var textListAdapter: TextListAdapter? = null

    override fun initVariable() {
        binding.viewModel = viewModel
    }

    override fun initView() {
        // recycler view
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        textListAdapter = TextListAdapter()
        binding.apply {
            rvTextList.apply {
                setHasFixedSize(true)
                this.layoutManager = layoutManager
                adapter = textListAdapter
            }
        }
        initSwipe()
    }

    override fun initListener() {
        with(binding) {
            etPuttext.doOnTextChanged { text, _, _, _ ->
                CoroutineScope(IO).launch {
                    viewModel?.getSearchTexts(text.toString())?.collect {
                        CoroutineScope(Main).launch {
                            textListAdapter?.setItem(it)
                        }
                        viewModel?.noDataNotification?.set(it.isEmpty())
                    }
                }
            }
            btnPuttext.setOnClickListener {
                viewModel?.insertText(tlPuttext.editText?.text.toString())
                tlPuttext.editText?.setText("")
                hideKeyboard()
            }
        }
    }

    override fun initObserver() {
        repeatOnStarted {
            viewModel.eventFlow.collect { event ->
                handleEvent(event)
            }
        }
        repeatOnStarted {
            viewModel.getAllTexts().collect{
                textListAdapter?.setItem(it)
            }
        }
    }

    private fun handleEvent(event: RoomViewModel.Event) = when (event) {
        is RoomViewModel.Event.ShowNotification -> {
            Utils.showNotification(event.msg, event.type)
        }
    }


    /**
     * Hiding keyboard
     */
    private fun hideKeyboard() {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private val p: Paint = Paint()

    private fun initSwipe() {
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT /* | ItemTouchHelper.RIGHT */) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT) {
                    textListAdapter?.getItem()?.get(position)?.let { viewModel.deleteText(it) }
                } else {
                    // 오른쪽으로 밀었을때
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                var icon: Bitmap
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView: View = viewHolder.itemView
                    val height =
                        itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3
                    if (dX > 0) {
                        // 오른쪽으로 밀었을 때
                    } else {
                        p.color = Color.parseColor("#D32F2F")
                        val background = RectF(
                            itemView.right.toFloat() + dX,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat()
                        )
                       // c.drawRect(background, p)
                        val radius = resources.getDimension(R.dimen._7dp)

                        c.drawRoundRect(background, radius, radius, p)
                        // icon
                        icon = BitmapFactory.decodeResource(resources, R.drawable.icon_delete)
                        val iconDest = RectF(
                            itemView.right - 2 * width,
                            itemView.top + width,
                            itemView.right - width,
                            itemView.bottom - width
                        )
                        c.drawBitmap(icon, null, iconDest, p)
                    }
                }

            }

        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvTextList)

    }
}