package com.prmto.person_detail_data.remote.datasource

import com.prmto.core_data.dispatcher.DispatcherProvider
import com.prmto.core_data.util.tryApiCall
import com.prmto.person_detail_data.remote.PersonApi
import com.prmto.person_detail_data.remote.dto.PersonDetailDto
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PersonRemoteDataSourceImpl @Inject constructor(
    private val personApi: PersonApi,
    private val dispatcherProvider: DispatcherProvider
) : PersonRemoteDataSource {
    override suspend fun getPersonDetail(personId: Int, language: String): PersonDetailDto {
        return withContext(dispatcherProvider.IO) {
            tryApiCall {
                personApi.getPersonDetail(
                    personId = personId,
                    language = language
                )
            }
        }
    }
}