package com.prmto.mova_movieapp.feature_home.data.remote.pagingSource.tv

import com.prmto.mova_movieapp.core.data.remote.dto.tv.TvSeriesDto
import com.prmto.mova_movieapp.core.data.remote.mapper.tv.toTvSeries
import com.prmto.mova_movieapp.core.data.util.paging.BasePagingSource
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import javax.inject.Inject

class TvPagingSource @Inject constructor(
    private val fetchTvSeries: suspend (page: Int) -> List<TvSeriesDto>
) : BasePagingSource<TvSeries>() {
    override suspend fun fetchData(page: Int): List<TvSeries> {
        return fetchTvSeries(page).map { it.toTvSeries() }
    }
}