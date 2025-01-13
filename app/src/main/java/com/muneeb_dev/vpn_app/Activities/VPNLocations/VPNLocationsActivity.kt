package com.muneeb_dev.vpn_app.Activities.VPNLocations

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.muneeb_dev.vpn_app.Activities.VPNLocations.Adapter.ServersRvAdapter
import com.muneeb_dev.vpn_app.AppUtils.updateStatusBarColor
import com.muneeb_dev.vpn_app.DataModels.Server_DataModel
import com.muneeb_dev.vpn_app.R
import com.muneeb_dev.vpn_app.databinding.ActivityVpnlocationsBinding

class VPNLocationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVpnlocationsBinding

    private lateinit var adapter: ServersRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVpnlocationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //status bar color
        updateStatusBarColor(getColor(R.color.app_main_bg_color),false)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //set toolbar
        binding.includedToolbar.appNameTV.text = "VPN locations"
//        binding.includedToolbar.actionBtnIMGV.visibility = View.VISIBLE
//        binding.includedToolbar.actionBtnIMGV.setImageResource(R.drawable.tick_icon)
//        binding.includedToolbar.actionBtnIMGV.setOnClickListener{}
        binding.includedToolbar.mainToolBar.setNavigationOnClickListener{
            finish()
        }


        // Initialize TabLayout
        setupTabs()

        // Initialize RecyclerView
        adapter = ServersRvAdapter(listOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Load initial data for "All Servers"
        loadTabData(0)

        // Tab Selection Listener
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
//                tab?.icon = ContextCompat.getDrawable(this@VPNLocationsActivity, R.drawable.blue_rect_rounded)
                loadTabData(tab?.position ?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.icon = null
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupTabs() {
        val tabAll = binding.tabLayout.newTab()
        tabAll.text = "All Servers"
//        tabAll.icon = ContextCompat.getDrawable(this, R.drawable.blue_rect_rounded)

        val tabPremium = binding.tabLayout.newTab()
        tabPremium.text = "Premium Servers"

        binding.tabLayout.addTab(tabAll)
        binding.tabLayout.addTab(tabPremium)
    }

    private fun loadTabData(tabPosition: Int) {
        val serverList = when (tabPosition) {
            0 -> getAllServers()
            1 -> getPremiumServers()
            else -> listOf()
        }
        adapter.updateData(serverList)
    }

    private fun getAllServers(): List<Server_DataModel> {
        return listOf(
            Server_DataModel("Singapore", "127.123.21.12", false),
            Server_DataModel("Netherlands", "127.123.21.12", false),
            Server_DataModel("US - New York", "127.123.21.12", true),
            Server_DataModel("Singapore", "127.123.21.12", false),
            Server_DataModel("Netherlands", "127.123.21.12", false),
            Server_DataModel("US - New York", "127.123.21.12", true),
            Server_DataModel("Singapore", "127.123.21.12", false),
            Server_DataModel("Netherlands", "127.123.21.12", false),
            Server_DataModel("Singapore", "127.123.21.12", false),
            Server_DataModel("Netherlands", "127.123.21.12", false),
            Server_DataModel("US - New York", "127.123.21.12", true),
            Server_DataModel("Singapore", "127.123.21.12", false),
            Server_DataModel("Netherlands", "127.123.21.12", false),
            Server_DataModel("US - New York", "127.123.21.12", true)
        )
    }

    private fun getPremiumServers(): List<Server_DataModel> {
        return listOf(
            Server_DataModel("US - New York", "127.123.21.12", true),
            Server_DataModel("California", "127.123.21.12", true),
            Server_DataModel("US - New York", "127.123.21.12", true),
            Server_DataModel("California", "127.123.21.12", true),
            Server_DataModel("US - New York", "127.123.21.12", true),
            Server_DataModel("California", "127.123.21.12", true),
            Server_DataModel("US - New York", "127.123.21.12", true),
            Server_DataModel("California", "127.123.21.12", true),
            Server_DataModel("US - New York", "127.123.21.12", true),
            Server_DataModel("California", "127.123.21.12", true),
            Server_DataModel("US - New York", "127.123.21.12", true),
            Server_DataModel("California", "127.123.21.12", true),
            Server_DataModel("US - New York", "127.123.21.12", true),
            Server_DataModel("California", "127.123.21.12", true)
        )
    }
}