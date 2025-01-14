package com.muneeb_dev.vpn_app.Fragments.HistoryFrag

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.muneeb_dev.vpn_app.Activities.HistoryDetailAct.HistoryDetailActivity
import com.muneeb_dev.vpn_app.Activities.VPNLocations.Adapter.ServersRvAdapter
import com.muneeb_dev.vpn_app.Fragments.HistoryFrag.Adapter.HistoryRvAdapter
import com.muneeb_dev.vpn_app.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

    private lateinit var adapter: HistoryRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater , container , false)

        // Initialize RecyclerView
        adapter = HistoryRvAdapter(){
            startActivity(Intent(requireContext() , HistoryDetailActivity::class.java))
        }
        binding.historyRV.layoutManager = LinearLayoutManager(requireContext())
        binding.historyRV.adapter = adapter

        return binding.root
    }

}