package com.prmto.mova_movieapp.core.data.local.converter

import androidx.room.TypeConverter

class DatabaseConverter {

    private val separator = ","


    @TypeConverter
    fun convertListToString(list: List<Int>): String {

        val stringBuilder = StringBuilder()

        if (list.isEmpty()) return "0"

        for (item in list) {
            stringBuilder.append(item).append(separator)
        }

        // When we convert to string, delete the separator end of the string
        stringBuilder.setLength(stringBuilder.length - separator.length)
        return stringBuilder.toString()
    }

    @TypeConverter
    fun convertStringToList(string: String): List<Int> {
        return string.split(separator).map { it.toInt() }
    }
}