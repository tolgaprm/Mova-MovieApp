package com.prmto.person_detail_data.repository

import com.prmto.core_data.util.safeApiCallReturnResource
import com.prmto.core_domain.util.Resource
import com.prmto.person_detail_data.remote.datasource.PersonRemoteDataSource
import com.prmto.person_detail_data.remote.mapper.toPersonDetail
import com.prmto.person_detail_domain.model.PersonDetail
import com.prmto.person_detail_domain.repository.PersonRepository
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