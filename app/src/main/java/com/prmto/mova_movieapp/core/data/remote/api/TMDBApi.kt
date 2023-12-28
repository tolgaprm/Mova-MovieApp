package com.prmto.mova_movieapp.core.data.remote.api

import com.prmto.mova_movieapp.core.data.remote.dto.genre.GenreListDto
import retrofit2.http.GET
import retrofit2.http.Query


interface TMDBApi {

    @GET("genre/movie/list")
    suspend fun getMovieGenreList(
        @Query("language") language: String
    ): GenreListDto

    @GET("genre/tv/list")
    suspend fun getTvGenreList(
        @Query("language") language: String
    ): GenreListDto
}