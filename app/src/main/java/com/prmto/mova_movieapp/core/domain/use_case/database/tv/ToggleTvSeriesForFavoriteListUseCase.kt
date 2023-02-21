package com.prmto.mova_movieapp.core.domain.use_case.database.tv

import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.domain.repository.LocalDatabaseRepository
import javax.inject.Inject

class ToggleTvSeriesForFavoriteListUseCase @Inject constructor(
    private val repository: LocalDatabaseRepository
) {
    suspend operator fun invoke(
        tvSeries: TvSeries,
        doesAddFavorite: Boolean
    ) {
        if (doesAddFavorite) {
            repository.deleteTvSeriesFromFavoriteList(favoriteTvSeries = tvSeries.toFavoriteTvSeries())
        } else {
            repository.insertTvSeriesToFavoriteList(favoriteTvSeries = tvSeries.toFavoriteTvSeries())
        }
    }
}