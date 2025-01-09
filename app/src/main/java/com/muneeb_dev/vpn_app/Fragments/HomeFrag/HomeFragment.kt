package com.muneeb_dev.vpn_app.Fragments.HomeFrag

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muneeb_dev.vpn_app.Activities.VPNLocations.VPNLocationsActivity
import com.muneeb_dev.vpn_app.R
import com.muneeb_dev.vpn_app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater , container , false)

        binding.selecteLocationCL.setOnClickListener {
            startActivity(Intent(requireContext() , VPNLocationsActivity::class.java))
        }


        return binding.root
    }
}