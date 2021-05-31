package com.example.cryptocurrencytracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencytracker.databinding.CoinItemBinding
import com.example.cryptocurrencytracker.model.Coin

class CoinListAdapter :
    ListAdapter<Coin, CoinListAdapter.CoinViewHolder>(CoinAdapterDiffUtilItemCallback) {

    var onClickListener: ((Coin) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding =
            CoinItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class CoinViewHolder(private val binding: CoinItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var boundCoin: Coin

        init {
            setupOnClickListener()
        }

        private fun setupOnClickListener() {
            itemView.setOnClickListener {
                onClickListener?.invoke(boundCoin)
            }
        }

        fun bind(coin: Coin) {
            boundCoin = coin
            binding.textViewName.text = coin.fullName
            binding.textViewSymbol.text = coin.symbol
        }
    }

    companion object CoinAdapterDiffUtilItemCallback : DiffUtil.ItemCallback<Coin>() {

        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }
}