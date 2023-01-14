package com.prmto.mova_movieapp.feature_person_detail.domain.util


import android.icu.text.SimpleDateFormat
import java.util.*

object DateFormatUtils {

    fun convertDateFormat(inputDate: String): String {
        val dateFormat = SimpleDateFormat("yyy-MM-dd", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

        val date = dateFormat.parse(inputDate)
        return outputDateFormat.format(date)
    }
}