package com.prmto.upcoming_data.remote.paging_source

import androidx.paging.PagingState
import com.prmto.core_data.remote.dto.movie.MovieDto
import com.prmto.core_data.util.paging.BasePagingSource
import com.prmto.upcoming_data.remote.mapper.toUpComingMovie
import com.prmto.upcoming_domain.model.UpcomingMovie
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