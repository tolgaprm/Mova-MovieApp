package com.prmto.mova_movieapp.core.domain.use_case.database.movie

import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.repository.local.LocalDatabaseRepository
import javax.inject.Inject

class ToggleMovieForWatchListUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository,
) {
    private val movieLocalRepository = repository.movieLocalRepository
    suspend operator fun invoke(
        movie: Movie,
        doesAddWatchList: Boolean,
    ) {
        if (doesAddWatchList) {
            movieLocalRepository.deleteMovieFromWatchListItem(movie = movie)
        } else {
            movieLocalRepository.insertMovieToWatchList(movie = movie)
        }
    }
}