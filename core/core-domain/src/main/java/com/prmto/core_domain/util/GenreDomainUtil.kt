package com.prmto.core_domain.util

import com.prmto.core_domain.models.genre.Genre

object GenreDomainUtils {

    fun getGenresBySeparatedByComma(genreIds: List<Int>, genres: List<Genre>?): String {
        if (genres == null) return ""
        return genreIds.joinToString(", ") { genreId ->
            genres.find { genre -> genre.id == genreId }?.name ?: ""
        }
    }

    fun handleConvertingGenreListToOneGenreString(
        genreList: List<Genre>,
        genreIds: List<Int>
    ): String {
        for (genre: Genre in genreList) {
            for (genreId: Int in genreIds) {
                if (genreId == genre.id) {
                    return genre.name
                }
                break
            }
        }
        return ""
    }
}