package com.lilyddang.lilycleanarchitecture.ui.room

import android.graphics.*
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lilyddang.lilycleanarchitecture.R
import com.lilyddang.lilycleanarchitecture.base.BaseActivity
import com.lilyddang.lilycleanarchitecture.databinding.ActivityRoomBinding
import com.lilyddang.lilycleanarchitecture.viewmodel.RoomViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.app.Activity
import android.view.inputmethod.InputMethodManager


class RoomActivity: BaseActivity<ActivityRoomBinding, RoomViewModel>(){

    override val layoutResID: Int = R.layout.activity_room
    override val viewModel by viewModel<RoomViewModel>()
    private var textListAdpater: TextListAdapter? = null

    override fun initVariable() {
        binding.viewModel = viewModel
    }
    override fun initView(){
        // recycler view
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        textListAdpater = TextListAdapter()
        binding.apply{
            rvTextList.apply{
                setHasFixedSize(true)
                this.layoutManager = layoutManager
                adapter = textListAdpater
            }
        }
        viewModel.getAllTexts()
        initSwipe()

    }
    override fun initListener() {
        binding.apply{
            etPuttext.doOnTextChanged { text, _, _, _ ->
                viewModel?.getSearchTexts(text.toString())
            }
            btnPuttext.setOnClickListener {
                viewModel?.insertText(tlPuttext.editText?.text.toString())
                tlPuttext.editText?.setText("")
                hideKeyboard()
            }
        }
    }
    override fun initObserver() {
        viewModel.apply{
            textListObservable.observe(this@RoomActivity,{
                textListAdpater?.setItem(viewModel.textList)
            })

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
                    textListAdpater?.getItem()?.get(position)?.let { viewModel.deleteText(it) }
                } else {
                    //오른쪽으로 밀었을때.
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
                        //오른쪽으로 밀었을 때
                    } else {
                        p.color = ContextCompat.getColor(this@RoomActivity,R.color.lily_pink_2)
                        val background = RectF(
                            itemView.right.toFloat() + dX,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, p)
                        /*
                         * icon 추가할 수 있음.
                         */
                        //icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_png); //vector 불가!
                        // RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        //c.drawBitmap(icon, null, icon_dest, p);
                    }
                }

            }

        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvTextList)

    }
}