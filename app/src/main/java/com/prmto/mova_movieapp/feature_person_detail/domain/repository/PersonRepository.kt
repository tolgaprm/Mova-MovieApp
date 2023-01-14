package com.prmto.mova_movieapp.feature_person_detail.domain.repository

import com.prmto.mova_movieapp.feature_person_detail.domain.model.PersonDetail

interface PersonRepository {

    suspend fun getPersonDetail(
        personId: Int,
        language: String
    ): PersonDetail
}