package com.prmto.mova_movieapp.core.data.util

import com.prmto.mova_movieapp.core.domain.models.genre.Genre

object HandleUtils {

    fun convertToYearFromDate(releaseDate: String?): String {
        if (releaseDate == null) return ""
        return releaseDate.split("-").first()
    }

    fun getGenresBySeparatedByComma(genreIds: List<Int>, genres: List<Genre>?): String {
        if (genres == null) return ""
        return genreIds.joinToString(", ") { genreId ->
            genres.find { genre -> genre.id == genreId }?.name ?: ""
        }
    }

    fun getGenresBySeparatedByComma(genreList: List<Genre>?): String {
        if (genreList == null) return ""
        return genreList.joinToString(", ") { genre -> genre.name }
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

    fun formatVoteCount(voteCount: Int?): String {
        if (voteCount == null) return ""
        if (voteCount < 1000) return voteCount.toString()
        val voteAverageInThousand = voteCount / 1000
        return "${voteAverageInThousand}k"
    }
}