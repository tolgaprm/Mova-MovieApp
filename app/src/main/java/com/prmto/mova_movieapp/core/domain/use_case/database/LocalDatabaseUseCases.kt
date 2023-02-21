package com.prmto.mova_movieapp.core.domain.use_case.database

import com.prmto.mova_movieapp.core.domain.use_case.GetMovieWatchListItemIdsUseCase
import com.prmto.mova_movieapp.core.domain.use_case.GetMoviesInWatchListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.database.movie.GetFavoriteMovieIdsUseCase
import com.prmto.mova_movieapp.core.domain.use_case.database.movie.GetFavoriteMoviesUseCase
import com.prmto.mova_movieapp.core.domain.use_case.database.movie.ToggleMovieForFavoriteListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.database.movie.ToggleMovieForWatchListUseCase
import com.prmto.mova_movieapp.core.domain.use_case.database.tv.*

data class LocalDatabaseUseCases(
    val clearAllDatabaseUseCase: ClearAllDatabaseUseCase,
    val toggleMovieForFavoriteListUseCase: ToggleMovieForFavoriteListUseCase,
    val toggleMovieForWatchListUseCase: ToggleMovieForWatchListUseCase,
    val getFavoriteMovieIdsUseCase: GetFavoriteMovieIdsUseCase,
    val getMovieWatchListItemIdsUseCase: GetMovieWatchListItemIdsUseCase,
    val toggleTvSeriesForFavoriteListUseCase: ToggleTvSeriesForFavoriteListUseCase,
    val toggleTvSeriesForWatchListItemUseCase: ToggleTvSeriesForWatchListItemUseCase,
    val getFavoriteTvSeriesIdsUseCase: GetFavoriteTvSeriesIdsUseCase,
    val getTvSeriesWatchListItemIdsUseCase: GetTvSeriesWatchListItemIdsUseCase,
    val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    val getFavoriteTvSeriesUseCase: GetFavoriteTvSeriesUseCase,
    val getMoviesInWatchListUseCase: GetMoviesInWatchListUseCase,
    val getTvSeriesInWatchListUseCase: GetTvSeriesInWatchListUseCase
)
