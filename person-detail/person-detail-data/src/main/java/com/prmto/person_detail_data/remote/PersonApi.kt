package com.prmto.person_detail_data.remote

import com.prmto.person_detail_data.remote.dto.PersonDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonApi {

    @GET("person/{person_id}")
    suspend fun getPersonDetail(
        @Path("person_id") personId: Int,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String = "combined_credits"
    ): Response<PersonDetailDto>
}