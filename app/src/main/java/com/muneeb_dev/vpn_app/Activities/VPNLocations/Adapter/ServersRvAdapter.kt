package com.muneeb_dev.vpn_app.Activities.VPNLocations.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muneeb_dev.vpn_app.DataModels.Server_DataModel
import com.muneeb_dev.vpn_app.databinding.ServerCountryRvItemBinding

class ServersRvAdapter(private var serverList: List<Server_DataModel>) : RecyclerView.Adapter<ServersRvAdapter.ServerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerViewHolder {
        val binding = ServerCountryRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServerViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = serverList.size

    class ServerViewHolder( binding: ServerCountryRvItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateData(newServerList: List<Server_DataModel>) {
        serverList = newServerList
        notifyDataSetChanged()
    }


}