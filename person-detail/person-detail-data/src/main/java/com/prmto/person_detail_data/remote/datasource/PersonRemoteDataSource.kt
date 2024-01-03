package com.prmto.person_detail_data.remote.datasource

import com.prmto.person_detail_data.remote.dto.PersonDetailDto

interface PersonRemoteDataSource {

    suspend fun getPersonDetail(
        personId: Int,
        language: String
    ): PersonDetailDto
}