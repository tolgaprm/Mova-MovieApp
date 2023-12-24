package com.prmto.mova_movieapp.feature_person_detail.data.repository

import com.prmto.mova_movieapp.core.data.util.safeApiCallReturnResource
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_person_detail.data.remote.datasource.PersonRemoteDataSource
import com.prmto.mova_movieapp.feature_person_detail.data.remote.dto.toPersonDetail
import com.prmto.mova_movieapp.feature_person_detail.domain.model.PersonDetail
import com.prmto.mova_movieapp.feature_person_detail.domain.repository.PersonRepository
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val personRemoteDataSource: PersonRemoteDataSource
) : PersonRepository {

    override suspend fun getPersonDetail(personId: Int, language: String): Resource<PersonDetail> {
        return safeApiCallReturnResource {
            personRemoteDataSource.getPersonDetail(
                personId = personId,
                language = language
            ).toPersonDetail()
        }
    }
}