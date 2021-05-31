package com.example.cryptocurrencytracker.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencytracker.data.CoinRepository
import com.example.cryptocurrencytracker.model.Coin
import com.example.cryptocurrencytracker.model.Result
import com.example.cryptocurrencytracker.model.dto.CoinHistoryDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val coinRepository: CoinRepository) :
    ViewModel() {
    private val _coin = MutableLiveData<Coin>()
    val coin: LiveData<Coin> get() = _coin
    private val _coinPrices = MutableLiveData<Result<Map<String, Double>>>()
    val coinPrices: LiveData<Result<Map<String, Double>>> get() = _coinPrices
    private val _coinHistory = MutableLiveData<List<CoinHistoryDto>>()
    val coinHistory: LiveData<List<CoinHistoryDto>> get() = _coinHistory

    fun loadCoinInfo(sym: String) {
        viewModelScope.launch {
            coinRepository.getCoin(sym).collect {
                _coin.value = it
            }
        }
    }

    fun loadCoinPrice(sym: String) {
        viewModelScope.launch {
            coinRepository.getCoinPrice(sym).collect {
                _coinPrices.value = it
            }
        }
    }

    fun loadCoinHistory(sym: String) {
        viewModelScope.launch {
            coinRepository.getCoinHistoryDaily(sym).collect {
                _coinHistory.value = it
            }
        }
    }
}