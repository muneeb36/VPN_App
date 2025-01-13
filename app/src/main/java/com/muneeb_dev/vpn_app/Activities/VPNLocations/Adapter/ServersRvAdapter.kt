package com.muneeb_dev.vpn_app.Activities.VPNLocations.Adapter

import android.view.LayoutInflater
import android.view.View
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
        val currentServer = serverList[position]
        if (currentServer.IsPremium)
        {
            holder.binding.premimumIMGV.visibility = View.VISIBLE
        }
        else {
            holder.binding.premimumIMGV.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = serverList.size

    class ServerViewHolder( val binding: ServerCountryRvItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateData(newServerList: List<Server_DataModel>) {
        serverList = newServerList
        notifyDataSetChanged()
    }


}
