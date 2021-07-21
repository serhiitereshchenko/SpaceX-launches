package com.serhii.launches.ui.launches

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class DateFormatter @Inject constructor() {

    private val formatter = SimpleDateFormat("d MMM yyyy HH:mm:ss", Locale.getDefault())

    fun formatDate(unix: Long): String {
        return formatter.format(Date(unix * 1000))
    }
}
