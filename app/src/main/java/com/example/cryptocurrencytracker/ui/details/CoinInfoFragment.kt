package com.example.cryptocurrencytracker.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.cryptocurrencytracker.R
import com.example.cryptocurrencytracker.databinding.FragmentCoinInfoBinding
import com.example.cryptocurrencytracker.model.Coin
import com.example.cryptocurrencytracker.model.Result
import com.example.cryptocurrencytracker.utils.UrlHelper

class CoinInfoFragment : Fragment() {
    private lateinit var binding: FragmentCoinInfoBinding
    private val detailsViewModel: DetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
    }

    private fun setupObserver() {
        detailsViewModel.coin.observe(viewLifecycleOwner, { coin ->
            coin?.let {
                updateUiCoinInfo(it)
            }
        })
        detailsViewModel.coinPrices.observe(viewLifecycleOwner, { coinPrices ->
            coinPrices?.let { result ->
                when (result.status) {
                    Result.Status.SUCCESS -> {
                        result.data?.let {
                            updateUiCoinPrice(it)
                        }
                        binding.loaderPrice.visibility = View.GONE
                    }

                    Result.Status.ERROR -> {
                        binding.loaderPrice.visibility = View.GONE
                    }

                    Result.Status.LOADING -> {
                        binding.loaderPrice.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun updateUiCoinInfo(coin: Coin) {
        binding.textViewName.text = coin.fullName
        val symbol = resources.getString(R.string.symbol) + " ${coin.symbol}"
        binding.textViewSymbol.text = symbol
        val algorithm = resources.getString(R.string.algorithm) + " ${coin.algorithm}"
        binding.textViewAlgorithm.text = algorithm
        val proofType = resources.getString(R.string.proof_type) + " ${coin.proofType}"
        binding.textViewProofType.text = proofType
        binding.textViewDescription.text = coin.description
        coin.imageUrl?.let {
            Glide.with(this)
                .load(UrlHelper.buildImageUrl(it))
                .fitCenter()
                .into(binding?.let { it.imageViewCoin })
        }
    }

    private fun updateUiCoinPrice(coinsPriceResponse: Map<String, Double>) {
        var prices = "Prices:\n"
        coinsPriceResponse.forEach {
            prices += it.key + " " + it.value + "\n"
        }
        binding.textViewPrice.text = prices
    }
}