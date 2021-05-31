package com.example.cryptocurrencytracker.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cryptocurrencytracker.databinding.FragmentCoinHistoryBinding
import com.example.cryptocurrencytracker.model.dto.CoinHistoryDto
import com.example.cryptocurrencytracker.utils.DateAndTimeHelper
import com.example.cryptocurrencytracker.utils.graph.DataPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CoinHistoryFragment : Fragment() {
    private lateinit var binding: FragmentCoinHistoryBinding
    private val detailsViewModel: DetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoinHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
    }

    private fun setupObserver() {
        detailsViewModel.coinHistory.observe(viewLifecycleOwner, {
            it?.let {
                if (it.isNotEmpty()) {
                    updateGraph(it)
                }
            }
        })
    }

    private fun updateGraph(coinHistory: List<CoinHistoryDto>) {
        GlobalScope.launch {
            val dataPoints: MutableList<DataPoint> = mutableListOf()
            var valueByDay = ""
            for (i in 0..10) {
                coinHistory[i].let {
                    dataPoints.add(DataPoint(i.toFloat(), it.open))
                    valueByDay += DateAndTimeHelper.formatMillisecondsToDateString(it.time * 1000) + "  :  " + it.open + "\n"
                }
            }
            coinHistory[10].close.let {
                dataPoints.add(DataPoint(11f, it))
            }
            binding.graphView.setData(dataPoints)
            CoroutineScope(Dispatchers.Main).launch {
                binding.textViewValueByDay.text = valueByDay
            }
        }
    }
}