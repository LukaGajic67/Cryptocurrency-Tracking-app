package com.example.cryptocurrencytracker.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrencytracker.databinding.FragmentHomeBinding
import com.example.cryptocurrencytracker.ui.adapter.CoinListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var coinAdapter: CoinListAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupRecyclerView()
        setupObserver()
    }

    private fun setupAdapter() {
        coinAdapter = CoinListAdapter()
        coinAdapter.onClickListener = { coin ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(coin.symbol)
            findNavController().navigate(action)
        }
    }

    private fun setupObserver() {
        homeViewModel.coins.observe(viewLifecycleOwner, Observer { list ->
            if (list.isNotEmpty())
            coinAdapter.submitList(list)
        })
    }

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            adapter = coinAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

    }

}