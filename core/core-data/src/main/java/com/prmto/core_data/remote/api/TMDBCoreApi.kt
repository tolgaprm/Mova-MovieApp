package com.prmto.core_data.remote.api

import com.prmto.core_data.remote.dto.genre.GenreListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBCoreApi {

    @GET("genre/movie/list")
    suspend fun getMovieGenreList(
        @Query("language") language: String
    ): GenreListDto

    @GET("genre/tv/list")
    suspend fun getTvGenreList(
        @Query("language") language: String
    ): GenreListDto
}