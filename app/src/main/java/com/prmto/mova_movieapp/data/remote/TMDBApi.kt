package com.prmto.mova_movieapp.data.remote

import com.prmto.mova_movieapp.data.models.ApiResponse
import com.prmto.mova_movieapp.data.models.GenreList
import com.prmto.mova_movieapp.data.models.MovieDto
import com.prmto.mova_movieapp.data.models.TvSeriesDto
import com.prmto.mova_movieapp.data.models.detail.movie.MovieDetailDto
import com.prmto.mova_movieapp.data.models.detail.tv.TvDetailDto
import com.prmto.mova_movieapp.data.models.enums.Category
import com.prmto.mova_movieapp.data.models.enums.Sort
import com.prmto.mova_movieapp.presentation.util.toDiscoveryQueryString
import com.prmto.mova_movieapp.util.Constants.API_KEY
import com.prmto.mova_movieapp.util.Constants.QUERY_APPEND_TO_RESPONSE
import com.prmto.mova_movieapp.util.Constants.STARTING_PAGE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TMDBApi {

    @GET("genre/movie/list")
    suspend fun getMovieGenreList(
        @Query("language") language: String
    ): GenreList

    @GET("genre/tv/list")
    suspend fun getTvGenreList(
        @Query("language") language: String
    ): GenreList

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("region") region: String,
        @Query("language") language: String
    ): ApiResponse<MovieDto>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = STARTING_PAGE,
        @Query("region") region: String,
        @Query("language") language: String
    ): ApiResponse<MovieDto>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = STARTING_PAGE,
        @Query("region") region: String,
        @Query("language") language: String
    ): ApiResponse<MovieDto>


    @GET("tv/popular")
    suspend fun getPopularTvs(
        @Query("page") page: Int = STARTING_PAGE,
        @Query("language") language: String,
    ): ApiResponse<TvSeriesDto>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvs(
        @Query("page") page: Int = STARTING_PAGE,
        @Query("language") language: String,
    ): ApiResponse<TvSeriesDto>


    @GET("discover/movie")
    suspend fun discoverMovie(
        @Query("page") page: Int = STARTING_PAGE,
        @Query("language") language: String,
        @Query("with_genres") genres: String = "",
        @Query("primary_release_year") releaseYear: Int,
        @Query("sort_by") sort: String = Sort.Popularity.toDiscoveryQueryString(Category.MOVIE)
    ): ApiResponse<MovieDto>


    @GET("discover/tv")
    suspend fun discoverTv(
        @Query("page") page: Int = STARTING_PAGE,
        @Query("language") language: String,
        @Query("with_genres") genres: String = "",
        @Query("first_air_date_year") firstAirDateYear: Int,
        @Query("sort_by") sort: String = Sort.Popularity.toDiscoveryQueryString(Category.MOVIE)
    ): ApiResponse<TvSeriesDto>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String = QUERY_APPEND_TO_RESPONSE
    ): MovieDetailDto

    @GET("tv/{tv_id}")
    suspend fun getTvDetail(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String = QUERY_APPEND_TO_RESPONSE
    ): TvDetailDto

}