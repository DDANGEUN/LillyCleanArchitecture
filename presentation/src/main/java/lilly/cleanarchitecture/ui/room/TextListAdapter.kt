package lilly.cleanarchitecture.ui.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import lilly.cleanarchitecture.R
import lilly.cleanarchitecture.domain.room.model.TextItem

class TextListAdapter: RecyclerView.Adapter<TextListAdapter.TextListViewHolder>() {

    private var items:List<TextItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextListViewHolder  =
        TextListViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false))


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
        fun bind(item: TextItem){
            val itemContent = itemView.findViewById<TextView>(R.id.content)
            val itemTime = itemView.findViewById<TextView>(R.id.time)
            itemContent.text = item.content
            itemTime.text = item.time
        }
    }
}