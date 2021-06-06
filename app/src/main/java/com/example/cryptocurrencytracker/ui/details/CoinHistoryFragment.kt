package com.example.cryptocurrencytracker.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cryptocurrencytracker.R
import com.example.cryptocurrencytracker.databinding.FragmentCoinHistoryBinding
import com.example.cryptocurrencytracker.model.dto.CoinHistoryDto
import com.example.cryptocurrencytracker.utils.DateAndTimeHelper
import com.example.cryptocurrencytracker.utils.HistoryType
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
    ): View {
        binding = FragmentCoinHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        initView()
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
            val valueMax = coinHistory.maxByOrNull { it.open }?.open
            val valueMin = coinHistory.minByOrNull { it.open }?.open
            val timeStart =
                DateAndTimeHelper.formatMillisecondsToDateString(coinHistory.first().time * 1000)
            val timeEnd =
                DateAndTimeHelper.formatMillisecondsToDateString(coinHistory.last().time * 1000)
            for (i in coinHistory.indices) {
                dataPoints.add(DataPoint(i.toDouble(), coinHistory[i].open))
            }
            binding.graphView.setData(dataPoints)
            CoroutineScope(Dispatchers.Main).launch {
                binding.loaderGraph.visibility = View.GONE
                if (valueMax != valueMin)
                    binding.textViewValueMax.text = valueMax.toString()
                binding.textViewValueMin.text = valueMin.toString()
                binding.textViewTimeStart.text = timeStart
                binding.textViewTimeEnd.text = timeEnd
            }
        }
    }

    private fun initView() {
        initTypePicker()
        binding.buttonLoadData.setOnClickListener {
            binding.loaderGraph.visibility = View.VISIBLE
            detailsViewModel.loadCoinHistory()
        }
    }

    private fun initTypePicker() {
        initLimitPickerDay()
        with(binding.numberPickerType) {
            minValue = 0
            maxValue = 2
            displayedValues = resources.getStringArray(R.array.coin_history_types)
            setOnValueChangedListener { _, _, newVal ->
                when (newVal) {
                    0 -> initLimitPickerDay()
                    1 -> initLimitPickerHour()
                    2 -> initLimitPickerMinute()
                }
            }
        }
    }

    private fun initLimitPickerDay() {
        with(binding.numberPickerLimit) {
            minValue = 0
            maxValue = 2
            value = 0
            displayedValues = resources.getStringArray(R.array.coin_limit_day)
            detailsViewModel.limit = "7"
            detailsViewModel.type = HistoryType.DAY
            setOnValueChangedListener { _, _, newVal ->
                when (newVal) {
                    0 -> detailsViewModel.limit = "7"
                    1 -> detailsViewModel.limit = "14"
                    2 -> detailsViewModel.limit = "30"
                }
            }
        }
    }

    private fun initLimitPickerHour() {
        with(binding.numberPickerLimit) {
            minValue = 0
            maxValue = 2
            value = 0
            displayedValues =
                resources.getStringArray(R.array.coin_limit_hour)
            detailsViewModel.limit = "24"
            detailsViewModel.type = HistoryType.HOUR
            setOnValueChangedListener { _, _, newVal ->
                when (newVal) {
                    0 -> detailsViewModel.limit = "24"
                    1 -> detailsViewModel.limit = "72"
                    2 -> detailsViewModel.limit = "720"
                }
            }
        }
    }

    private fun initLimitPickerMinute() {
        with(binding.numberPickerLimit) {
            minValue = 0
            maxValue = 2
            value = 0
            displayedValues =
                resources.getStringArray(R.array.coin_limit_minute)
            detailsViewModel.limit = "60"
            detailsViewModel.type = HistoryType.MINUTE
            setOnValueChangedListener { _, _, newVal ->
                when (newVal) {
                    0 -> detailsViewModel.limit = "60"
                    1 -> detailsViewModel.limit = "180"
                    2 -> detailsViewModel.limit = "1440"
                }
            }
        }
    }
}