package com.prmto.mova_movieapp.data.remote

import com.prmto.mova_movieapp.domain.models.Genre
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {

    @GET("/genre/movie/list")
    suspend fun getMovieGenreList(
        @Query("language") language: String
    ): List<Genre>

    @GET("/genre/tv/list")
    suspend fun getTvGenreList(
        @Query("language") language: String
    ): List<Genre>

}