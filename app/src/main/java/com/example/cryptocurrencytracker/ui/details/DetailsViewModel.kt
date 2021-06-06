package com.example.cryptocurrencytracker.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencytracker.data.CoinRepository
import com.example.cryptocurrencytracker.model.Coin
import com.example.cryptocurrencytracker.model.Result
import com.example.cryptocurrencytracker.model.dto.CoinHistoryDto
import com.example.cryptocurrencytracker.utils.HistoryType
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
    var sym: String = "BTC"
    var limit: String = "7"
    var type: HistoryType = HistoryType.DAY

    fun loadCoinInfo() {
        viewModelScope.launch {
            coinRepository.getCoin(sym).collect {
                _coin.value = it
            }
        }
    }

    fun loadCoinPrice() {
        viewModelScope.launch {
            coinRepository.getCoinPrice(sym).collect {
                _coinPrices.value = it
            }
        }
    }

    fun loadCoinHistory() {
        viewModelScope.launch {
            coinRepository.getCoinHistory(type, sym, limit).collect {
                _coinHistory.value = it
            }
        }
    }
}