package com.prmto.mova_movieapp.feature_upcoming.data.remote.datasource

import com.prmto.mova_movieapp.core.data.remote.dto.movie.MovieDto
import com.prmto.mova_movieapp.core.data.util.tryApiCall
import com.prmto.mova_movieapp.feature_upcoming.data.remote.api.UpcomingApi
import com.prmto.mova_movieapp.feature_upcoming.data.remote.dto.UpComingApiResponse
import javax.inject.Inject

class UpcomingMovieRemoteDataSourceImpl @Inject constructor(
    private val upComingApi: UpcomingApi
) : UpcomingMovieRemoteDataSource {
    override suspend fun getUpComingMovies(
        page: Int,
        language: String
    ): UpComingApiResponse<MovieDto> {
        return tryApiCall { upComingApi.getUpComingMovies(page = page, language = language) }
    }
}