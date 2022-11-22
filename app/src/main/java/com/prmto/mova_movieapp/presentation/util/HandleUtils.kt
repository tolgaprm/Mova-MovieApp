package com.prmto.mova_movieapp.presentation.util

import com.prmto.mova_movieapp.data.models.Genre
import com.prmto.mova_movieapp.domain.models.Movie

object HandleUtils {
    fun handleReleaseDate(releaseDate: String): String {
        return releaseDate.split("-")[0]
    }


    fun handleGenreText(movieGenreList: List<Genre>, movie: Movie): String {
        var genreNames = ""

        if (movie.genreIds.isEmpty()) {
            return ""
        }

        movieGenreList.forEach { genre ->
            movie.genreIds.forEach {
                if (it == genre.id) {
                    genreNames += "${genre.name}, "
                }
            }
        }

        if (genreNames.isNotEmpty()) {
            return genreNames.subSequence(0, genreNames.length - 2).toString()
        }

        return genreNames
    }

    fun handleGenreText(genreList: List<Genre>): String {
        var genreNames = ""
        if (genreList.isEmpty()) {
            return ""
        }

        genreList.forEach { genre ->
            genreNames += "${genre.name}, "
        }

        if (genreNames.isNotEmpty()) {
            return genreNames.subSequence(0, genreNames.length - 2).toString()
        }

        return genreNames
    }

    fun handleGenreOneText(movieGenreList: List<Genre>, genreIds: List<Int>): String {

        for (genre: Genre in movieGenreList) {
            for (genreId: Int in genreIds) {
                if (genreId == genre.id) {
                    return genre.name
                }
                break

            }
        }

        return ""

    }

    fun handleVoteCount(voteCount: Int): String {
        if (voteCount < 1000)
            return voteCount.toString()

        val voteCountText: Int?

        val divide = (voteCount / 1000).toFloat()

        val mod = voteCount % 1000

        if (mod > 100) {
            voteCountText = (mod / 100)
        } else {
            return "${divide.toInt()} k"
        }

        return "${divide.toInt()}.$voteCountText k"
    }


}