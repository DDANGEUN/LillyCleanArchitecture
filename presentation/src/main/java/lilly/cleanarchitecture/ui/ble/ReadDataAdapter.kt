package lilly.cleanarchitecture.ui.ble

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import lilly.cleanarchitecture.R
import java.util.ArrayList

class ReadDataAdapter: RecyclerView.Adapter<ReadDataAdapter.ReadDataViewHolder>() {

    lateinit var mContext: Context
    lateinit var itemView: View
    private var items = java.util.ArrayList<Pair<String, String>>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReadDataViewHolder {
        mContext = parent.context
        itemView = LayoutInflater.from(mContext).inflate(R.layout.item_ble_read, parent, false)
        return ReadDataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReadDataViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun setItem(item: ArrayList<Pair<String,String>>){
        items = item
        notifyDataSetChanged()
    }

    inner class ReadDataViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: Pair<String,String>){
            val time = itemView.findViewById<TextView>(R.id.tv_item_read_time)
            val data = itemView.findViewById<TextView>(R.id.tv_item_read_data)

            time.text = item.first
            data.text = item.second

        }
    }
}