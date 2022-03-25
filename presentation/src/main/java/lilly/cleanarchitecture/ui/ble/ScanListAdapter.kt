package lilly.cleanarchitecture.ui.ble

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polidea.rxandroidble2.scan.ScanResult
import lilly.cleanarchitecture.databinding.ItemScanlistBinding
import java.util.*

class ScanListAdapter
    : RecyclerView.Adapter<ScanListAdapter.ScanListViewHolder>(){

    private var items = HashMap<String, ScanResult>()
    private lateinit var itemClickListner: ItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanListViewHolder =
        ScanListViewHolder(ItemScanlistBinding.inflate(LayoutInflater.from(parent.context), parent, false))

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

    inner class ScanListViewHolder(private val binding: ItemScanlistBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(scanResult: ScanResult) {
            with(binding){
                scanResultItem = scanResult
                executePendingBindings()
            }
            binding.btnScanlistConnect.setOnClickListener {
                itemClickListner.onClick(it,scanResult)
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