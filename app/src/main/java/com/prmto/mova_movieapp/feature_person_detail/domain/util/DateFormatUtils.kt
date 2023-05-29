package com.prmto.mova_movieapp.feature_person_detail.domain.util


import android.icu.text.SimpleDateFormat
import java.text.ParseException
import java.util.Locale

object DateFormatUtils {

    fun convertDateFormat(inputDate: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())

        return try {
            val date = dateFormat.parse(inputDate)
            outputDateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }
}