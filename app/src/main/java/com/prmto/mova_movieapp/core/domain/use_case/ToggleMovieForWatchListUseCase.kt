package com.prmto.mova_movieapp.core.domain.use_case

import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.repository.LocalDatabaseRepository
import javax.inject.Inject

class ToggleMovieForWatchListUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {
    suspend operator fun invoke(
        movie: Movie,
        doesAddWatchList: Boolean
    ) {
        if (doesAddWatchList) {
            repository.deleteMovieFromWatchListItem(movieWatchListItem = movie.toMovieWatchListItem())
        } else {
            repository.insertMovieToWatchList(movieWatchListItem = movie.toMovieWatchListItem())
        }
    }
}