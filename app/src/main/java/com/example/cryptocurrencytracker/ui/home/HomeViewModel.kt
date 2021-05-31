package com.example.cryptocurrencytracker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencytracker.data.CoinRepository
import com.example.cryptocurrencytracker.model.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val coinRepository: CoinRepository) :
    ViewModel() {
    private val _coins = MutableLiveData<List<Coin>>()
    val coins: LiveData<List<Coin>> get() = _coins

    init {
        fetchCoins()
    }

    fun fetchCoins() {
        viewModelScope.launch {
            coinRepository.getAllCoins().collect {
                _coins.value = it
            }
        }
    }
}