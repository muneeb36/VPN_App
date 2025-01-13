package com.muneeb_dev.vpn_app.Activities.MainAct

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.muneeb_dev.vpn_app.Activities.MainAct.PagerAdapter.ViewPagerAdapter
import com.muneeb_dev.vpn_app.Activities.SpeedTestAct.SpeedTestActivity
import com.muneeb_dev.vpn_app.AppUtils.updateStatusBarColor
import com.muneeb_dev.vpn_app.R
import com.muneeb_dev.vpn_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //status bar color
        updateStatusBarColor(getColor(R.color.app_main_bg_color),false)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Set up ViewPager with Adapter
        val pagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
        // Disable user input (scrolling)
        binding.viewPager.isUserInputEnabled = false

        // Handle Bottom Navigation clicks
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home ->
                {
                    binding.viewPager.setCurrentItem(0, true)

                }
                R.id.nav_bolt ->
                {
                    binding.viewPager.setCurrentItem(1, true)
                }
            }
            true
        }

        // Sync ViewPager with Bottom Navigation
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNavigationView.menu.getItem(position).isChecked = true
            }
        })

        // Handle Navigation Drawer clicks
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.drawer_home -> { /* Handle Home */ }
                R.id.drawer_settings -> { /* Handle Settings */ }
                R.id.drawer_about -> { /* Handle About */ }
            }
            true
        }

        // Set click listener on the ImageView
        binding.drawerIMGV.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START) // Close drawer
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START) // Open drawer
            }
        }

        binding.includedDrawer.speedTestLL.setOnClickListener {
            startActivity(Intent(this, SpeedTestActivity::class.java))
        }


    }




}