package com.prmto.mova_movieapp.feature_person_detail.data.remote.datasource

import com.prmto.mova_movieapp.core.data.util.tryApiCall
import com.prmto.mova_movieapp.feature_person_detail.data.remote.PersonApi
import com.prmto.mova_movieapp.feature_person_detail.data.remote.dto.PersonDetailDto
import javax.inject.Inject

class PersonRemoteDataSourceImpl @Inject constructor(
    private val personApi: PersonApi
) : PersonRemoteDataSource {
    override suspend fun getPersonDetail(personId: Int, language: String): PersonDetailDto {
        return tryApiCall {
            personApi.getPersonDetail(
                personId = personId,
                language = language
            )
        }
    }
}