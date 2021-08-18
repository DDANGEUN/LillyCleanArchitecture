package com.lilyddang.lilycleanarchitecture.ui.room

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lilyddang.lilycleanarchitecture.R
import com.lilyddang.lilycleanarchitecture.domain.model.TextItem

class TextListAdapter: RecyclerView.Adapter<TextListAdapter.TextListViewHolder>() {

    lateinit var mContext: Context
    lateinit var itemView: View
    private var items:List<TextItem> = ArrayList()



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TextListAdapter.TextListViewHolder {
        mContext = parent.context
        itemView = LayoutInflater.from(mContext).inflate(
            R.layout.item_text,
            parent,
            false
        )
        return TextListViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: TextListAdapter.TextListViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(): List<TextItem>{
        return items
    }

    fun setItem(item: List<TextItem>){
        items = item
        notifyDataSetChanged()
    }

    inner class TextListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        @SuppressLint("SetTextI18n")
        fun bind(item: TextItem){
            val itemContent = itemView.findViewById<TextView>(R.id.content)
            val itemTime = itemView.findViewById<TextView>(R.id.time)
            itemContent.text = item.content
            itemTime.text = item.time
        }
    }
}