package com.prmto.mova_movieapp.core.domain.use_case

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
