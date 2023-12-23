package com.prmto.mova_movieapp.feature_upcoming.data.remote.paging_source

import androidx.paging.PagingState
import com.prmto.mova_movieapp.core.data.dto.MovieDto
import com.prmto.mova_movieapp.core.data.paging.BasePagingSource
import com.prmto.mova_movieapp.feature_upcoming.data.remote.mapper.toUpComingMovie
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingMovie
import javax.inject.Inject

class UpcomingMoviePagingSource @Inject constructor(
    private val fetchUpcomingMovie: suspend (page: Int) -> List<MovieDto>
) : BasePagingSource<UpcomingMovie>() {
    override fun getRefreshKey(state: PagingState<Int, UpcomingMovie>): Int? {
        return state.anchorPosition
    }

    override suspend fun fetchData(page: Int): List<UpcomingMovie> {
        return fetchUpcomingMovie(page).map { it.toUpComingMovie() }
    }
}