package com.muneeb_dev.vpn_app.Fragments.HomeFrag

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.muneeb_dev.vpn_app.Activities.HistoryDetailAct.HistoryDetailActivity
import com.muneeb_dev.vpn_app.Activities.VPNLocations.VPNLocationsActivity
import com.muneeb_dev.vpn_app.AppUtils.onConnectClicked
import com.muneeb_dev.vpn_app.R
import com.muneeb_dev.vpn_app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private var isConnected:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater , container , false)

        binding.selecteLocationCL.setOnClickListener {
            startActivity(Intent(requireContext() , VPNLocationsActivity::class.java))
        }

        showConnectedViews(false)

        binding.connectionLL.setOnClickListener {
            showConnectedViews(true)
            onConnectClicked?.invoke(true)
        }

        binding.disconnectTV.setOnClickListener {
            onConnectClicked?.invoke(false)
            showConnectedViews(false)
        }
        return binding.root
    }

    private fun showConnectedViews(isConnected:Boolean)
    {
        if (isConnected) {
            binding.downloadLL.visibility = View.VISIBLE
            binding.uploadLL.visibility = View.VISIBLE
            binding.timmerCardV.visibility = View.VISIBLE
            binding.gethoursTV.visibility = View.VISIBLE
            binding.extraTimeTV.visibility = View.VISIBLE
            binding.disconnectTV.visibility = View.VISIBLE
            binding.statusTV.text = "Connected"
            binding.statusTV.setTextColor(resources.getColor( R.color.green3_color))

            binding.lottieAnimationView.cancelAnimation()
            binding.lottieAnimationView.visibility = View.GONE
            binding.connectionLL.visibility = View.GONE
            binding.selecteLocationCL.visibility = View.GONE




        } else {
            binding.downloadLL.visibility = View.GONE
            binding.uploadLL.visibility = View.GONE
            binding.timmerCardV.visibility = View.GONE
            binding.gethoursTV.visibility = View.GONE
            binding.extraTimeTV.visibility = View.GONE
            binding.disconnectTV.visibility = View.GONE
            binding.statusTV.text = "Disconnected"
            binding.statusTV.setTextColor(resources.getColor( R.color.red_color))

            binding.selecteLocationCL.visibility = View.VISIBLE
            binding.lottieAnimationView.visibility = View.VISIBLE // Make the view visible
            binding.lottieAnimationView.playAnimation()
            binding.connectionLL.visibility = View.VISIBLE

        }

    }
}