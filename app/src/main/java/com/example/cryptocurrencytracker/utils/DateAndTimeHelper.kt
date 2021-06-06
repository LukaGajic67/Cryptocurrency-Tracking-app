package com.example.cryptocurrencytracker.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateAndTimeHelper {
    /**
     * Formats milliseconds (Long) to dd/MM/yyyy - EU date format (String)
     */
    @SuppressLint("SimpleDateFormat")
    fun formatMillisecondsToDateString(milliseconds: Long): String {
        val formatter = SimpleDateFormat("dd MMM - HH:mm:ss")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliseconds
        return formatter.format(calendar.time)
    }
}