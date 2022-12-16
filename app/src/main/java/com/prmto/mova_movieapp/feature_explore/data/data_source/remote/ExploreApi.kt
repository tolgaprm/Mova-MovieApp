package com.prmto.mova_movieapp.feature_explore.data.data_source.remote

import com.prmto.mova_movieapp.core.data.dto.ApiResponse
import com.prmto.mova_movieapp.core.data.models.enums.Category
import com.prmto.mova_movieapp.core.data.models.enums.Sort
import com.prmto.mova_movieapp.core.presentation.util.toDiscoveryQueryString
import com.prmto.mova_movieapp.core.util.Constants
import com.prmto.mova_movieapp.feature_home.data.dto.MovieDto
import com.prmto.mova_movieapp.feature_home.data.dto.TvSeriesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ExploreApi {
    @GET("discover/movie")
    suspend fun discoverMovie(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String,
        @Query("with_genres") genres: String = "",
        @Query("primary_release_year") releaseYear: Int,
        @Query("sort_by") sort: String = Sort.Popularity.toDiscoveryQueryString(Category.MOVIE)
    ): ApiResponse<MovieDto>


    @GET("discover/tv")
    suspend fun discoverTv(
        @Query("page") page: Int = Constants.STARTING_PAGE,
        @Query("language") language: String,
        @Query("with_genres") genres: String = "",
        @Query("first_air_date_year") firstAirDateYear: Int,
        @Query("sort_by") sort: String = Sort.Popularity.toDiscoveryQueryString(Category.MOVIE)
    ): ApiResponse<TvSeriesDto>

}