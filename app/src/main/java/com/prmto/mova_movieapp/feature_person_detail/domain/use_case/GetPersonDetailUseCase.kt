package com.prmto.mova_movieapp.feature_person_detail.domain.use_case

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_person_detail.domain.model.PersonDetail
import com.prmto.mova_movieapp.feature_person_detail.domain.repository.PersonRepository
import com.prmto.mova_movieapp.feature_person_detail.domain.util.DateFormatUtils
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetPersonDetailUseCase @Inject constructor(
    private val repository: PersonRepository
) {

    suspend operator fun invoke(
        personId: Int,
        language: String
    ): Resource<PersonDetail> {
        return try {
            val result = repository.getPersonDetail(personId = personId, language = language)
            Resource.Success(
                data = result.copy(
                    birthday = DateFormatUtils.convertDateFormat(inputDate = result.birthday),
                    deathday = if (result.deathday != null) DateFormatUtils.convertDateFormat(
                        inputDate = result.deathday
                    ) else null,
                    combinedCredits = result.combinedCredits.copy(
                        cast = result.combinedCredits.cast.sortedByDescending { it.popularity },
                        crew = result.combinedCredits.crew.filter { it.department == "Directing" }
                            .sortedByDescending { it.popularity }
                    )
                )
            )
        } catch (e: IOException) {
            Resource.Error(UiText.StringResource(R.string.internet_error))
        } catch (e: HttpException) {
            Resource.Error(UiText.StringResource(R.string.oops_something_went_wrong))
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(UiText.StringResource(R.string.oops_something_went_wrong))
        }
    }
}