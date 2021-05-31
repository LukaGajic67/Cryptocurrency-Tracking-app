package com.example.cryptocurrencytracker.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cryptocurrencytracker.ui.details.CoinHistoryFragment
import com.example.cryptocurrencytracker.ui.details.CoinInfoFragment

class DetailsViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return CoinInfoFragment()
            1 -> return CoinHistoryFragment()
        }
        return CoinInfoFragment()
    }

}