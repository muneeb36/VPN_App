package com.muneeb_dev.vpn_app.Activities.MainAct.PagerAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.muneeb_dev.vpn_app.Fragments.BoltFrag.BoltFragment
import com.muneeb_dev.vpn_app.Fragments.HomeFrag.HomeFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> BoltFragment()
            else -> throw IllegalStateException("Invalid position $position")
        }
    }
}
