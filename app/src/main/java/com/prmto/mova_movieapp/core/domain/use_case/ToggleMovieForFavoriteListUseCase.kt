package com.prmto.mova_movieapp.core.domain.use_case

import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.repository.LocalDatabaseRepository
import javax.inject.Inject

class ToggleMovieForFavoriteListUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {
    suspend operator fun invoke(
        movie: Movie,
        doesAddFavoriteList: Boolean
    ) {
        if (doesAddFavoriteList) {
            repository.deleteMovieFromFavoriteList(favoriteMovie = movie.toFavoriteMovie())
        } else {
            repository.insertMovieToFavoriteList(favoriteMovie = movie.toFavoriteMovie())
        }
    }
}