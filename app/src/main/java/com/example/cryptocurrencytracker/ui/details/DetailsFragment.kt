package com.example.cryptocurrencytracker.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.cryptocurrencytracker.R
import com.example.cryptocurrencytracker.databinding.FragmentDetailsBinding
import com.example.cryptocurrencytracker.ui.adapter.DetailsViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val detailsViewModel: DetailsViewModel by activityViewModels()
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var titles: Array<String>
    private lateinit var adapter: DetailsViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsViewModel.sym = args.sym
        detailsViewModel.loadCoinInfo()
        detailsViewModel.loadCoinPrice()
        detailsViewModel.loadCoinHistory()
        setupTabs()
    }

    private fun setupTabs() {
        titles = resources.getStringArray(R.array.titles_details_tabs)
        adapter = DetailsViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

}