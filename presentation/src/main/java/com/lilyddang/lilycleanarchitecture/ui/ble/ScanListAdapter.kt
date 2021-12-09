package com.lilyddang.lilycleanarchitecture.ui.ble

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lilyddang.lilycleanarchitecture.R
import com.polidea.rxandroidble2.scan.ScanResult
import java.util.*
import java.util.concurrent.TimeUnit

class ScanListAdapter
    : RecyclerView.Adapter<ScanListAdapter.ScanListViewHolder>(){

    private lateinit var mContext: Context
    private var items = HashMap<String, ScanResult>()
    private lateinit var itemClickListner: ItemClickListener
    lateinit var itemView: View


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanListViewHolder {
        mContext = parent.context
        itemView = LayoutInflater.from(mContext).inflate(R.layout.item_scanlist, parent, false)

        return ScanListViewHolder(itemView)

    }
    override fun onBindViewHolder(holder: ScanListViewHolder, position: Int) {
        var i = 0
        for (key in items.keys) {
            if (position == i) {
                items[key]?.let { holder.bind(it) }
                break
            }
            i++
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
    fun setItem (item: HashMap<String, ScanResult>){
        items = item
        notifyDataSetChanged()
    }

    inner class ScanListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(scanResult: ScanResult) {
            val name = scanResult.bleDevice?.name?: ""
            val bleName = itemView.findViewById<TextView>(R.id.tv_scanlist_name)
            if(name.isEmpty()){
                bleName.text = "N/A"
                bleName.setTextColor(mContext.getColor(R.color.lilly_gray_2))
            }else{
                bleName.text = name
                bleName.setTextColor(mContext.getColor(R.color.lilly_blue_2))
            }
            val macAddress = itemView.findViewById<TextView>(R.id.tv_scanlist_mac)
            macAddress.text = scanResult.bleDevice.macAddress
            val rssi = itemView.findViewById<TextView>(R.id.tv_scanlist_rssi)
            rssi.text = "${scanResult.rssi}dBm"
            val btnExpand = itemView.findViewById<ImageButton>(R.id.btn_scanlist_expand)
            btnExpand.setOnClickListener {
                // TODO
            }
        }
    }
    interface ItemClickListener {
        fun onClick(view: View, scanResult: ScanResult)
    }
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }

}