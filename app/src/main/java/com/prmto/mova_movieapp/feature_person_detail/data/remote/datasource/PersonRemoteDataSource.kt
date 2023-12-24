package com.prmto.mova_movieapp.feature_person_detail.data.remote.datasource

import com.prmto.mova_movieapp.feature_person_detail.data.remote.dto.PersonDetailDto

interface PersonRemoteDataSource {

    suspend fun getPersonDetail(
        personId: Int,
        language: String
    ): PersonDetailDto
}