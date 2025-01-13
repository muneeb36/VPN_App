package com.muneeb_dev.vpn_app.Activities.HistoryDetailAct

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.muneeb_dev.vpn_app.AppUtils.updateStatusBarColor
import com.muneeb_dev.vpn_app.R
import com.muneeb_dev.vpn_app.databinding.ActivityHistoryDetailBinding

class HistoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //status bar color
        updateStatusBarColor(getColor(R.color.app_main_bg_color),false)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //set toolbar
        binding.includedToolbar.appNameTV.text = "History Detail"
//        binding.includedToolbar.actionBtnIMGV.visibility = View.VISIBLE
//        binding.includedToolbar.actionBtnIMGV.setImageResource(R.drawable.tick_icon)
//        binding.includedToolbar.actionBtnIMGV.setOnClickListener{}
        binding.includedToolbar.mainToolBar.setNavigationOnClickListener{
            finish()
        }



    }
}