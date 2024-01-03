package com.prmto.person_detail_domain.use_case

import com.prmto.core_domain.util.DateFormatUtils
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.UiText
import com.prmto.person_detail_domain.model.PersonDetail
import com.prmto.person_detail_domain.repository.PersonRepository
import javax.inject.Inject

class GetPersonDetailUseCase @Inject constructor(
    private val repository: PersonRepository
) {

    suspend operator fun invoke(
        personId: Int,
        language: String
    ): Resource<PersonDetail> {
        val resource = repository.getPersonDetail(personId = personId, language = language)

        return when (resource) {
            is Resource.Success -> {
                resource.data?.let { personDetail ->
                    val result = personDetail.copy(
                        birthday = DateFormatUtils.convertDateFormat(inputDate = personDetail.birthday),
                        deathday = if (personDetail.deathday != null) DateFormatUtils.convertDateFormat(
                            inputDate = personDetail.deathday
                        ) else null,
                        combinedCredits = personDetail.combinedCredits?.copy(
                            cast = personDetail.combinedCredits.cast.sortedByDescending { it.popularity },
                            crew = personDetail.combinedCredits.crew.filter { it.department == "Directing" }
                                .sortedByDescending { it.popularity }
                        )
                    )
                    Resource.Success(result)
                } ?: Resource.Error(uiText = UiText.somethingWentWrong())
            }

            is Resource.Error -> {
                Resource.Error(resource.uiText ?: UiText.somethingWentWrong())
            }
        }
    }
}