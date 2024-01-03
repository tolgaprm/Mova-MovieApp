package com.prmto.person_detail_domain.repository

import com.prmto.core_domain.util.Resource
import com.prmto.person_detail_domain.model.PersonDetail

interface PersonRepository {

    suspend fun getPersonDetail(
        personId: Int,
        language: String
    ): Resource<PersonDetail>
}