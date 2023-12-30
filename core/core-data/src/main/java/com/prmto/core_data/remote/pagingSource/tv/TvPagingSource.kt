package com.prmto.core_data.remote.pagingSource.tv

import com.prmto.core_data.remote.dto.tv.TvSeriesDto
import com.prmto.core_data.remote.mapper.tv.toTvSeries
import com.prmto.core_data.util.paging.BasePagingSource
import com.prmto.core_domain.models.tv.TvSeries
import javax.inject.Inject

class TvPagingSource @Inject constructor(
    private val fetchTvSeries: suspend (page: Int) -> List<TvSeriesDto>
) : BasePagingSource<TvSeries>() {
    override suspend fun fetchData(page: Int): List<TvSeries> {
        return fetchTvSeries(page).map { it.toTvSeries() }
    }
}