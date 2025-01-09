package com.muneeb_dev.vpn_app.Fragments.BoltFrag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muneeb_dev.vpn_app.R
import com.muneeb_dev.vpn_app.databinding.FragmentBoltBinding
import com.muneeb_dev.vpn_app.databinding.FragmentHomeBinding


class BoltFragment : Fragment() {
    private lateinit var binding: FragmentBoltBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBoltBinding.inflate(inflater , container , false)


        return binding.root
    }

}