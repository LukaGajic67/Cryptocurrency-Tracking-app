package com.example.cryptocurrencytracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cryptocurrencytracker.R
import com.example.cryptocurrencytracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}