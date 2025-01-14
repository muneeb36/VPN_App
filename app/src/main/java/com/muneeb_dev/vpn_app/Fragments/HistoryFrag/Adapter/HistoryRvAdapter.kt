package com.muneeb_dev.vpn_app.Fragments.HistoryFrag.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muneeb_dev.vpn_app.DataModels.Server_DataModel
import com.muneeb_dev.vpn_app.databinding.HistoryRvItemBinding
import com.muneeb_dev.vpn_app.databinding.ServerCountryRvItemBinding

class HistoryRvAdapter(private val onItemClick: () -> Unit) : RecyclerView.Adapter<HistoryRvAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            onItemClick()
        }
    }

//    override fun getItemCount(): Int = serverList.size
    override fun getItemCount(): Int = 5

    class HistoryViewHolder(val binding: HistoryRvItemBinding) : RecyclerView.ViewHolder(binding.root)


}
