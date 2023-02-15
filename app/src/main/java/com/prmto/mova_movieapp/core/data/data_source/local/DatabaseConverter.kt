package com.prmto.mova_movieapp.core.data.data_source.local

import androidx.room.TypeConverter

class DatabaseConverter {

    private val separator = ","


    @TypeConverter
    fun convertListToString(list: List<Int>): String {

        val stringBuilder = StringBuilder()

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