package com.prmto.mova_movieapp.core.domain.util

import androidx.paging.PagingData
import androidx.paging.map
import com.prmto.mova_movieapp.core.domain.models.genre.Genre
import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun combineTvAndGenreMapOneGenre(
    tvGenreResourceFlow: Flow<List<Genre>>,
    tvPagingDataFlow: Flow<PagingData<TvSeries>>
): Flow<PagingData<TvSeries>> {
    return combine(
        tvPagingDataFlow,
        tvGenreResourceFlow
    ) { tvPagingData, tvGenreList ->
        tvPagingData.map { tv ->
            tv.copy(
                genreByOne = GenreDomainUtils.handleConvertingGenreListToOneGenreString(
                    genreList = tvGenreList,
                    genreIds = tv.genreIds
                )
            )
        }
    }
}