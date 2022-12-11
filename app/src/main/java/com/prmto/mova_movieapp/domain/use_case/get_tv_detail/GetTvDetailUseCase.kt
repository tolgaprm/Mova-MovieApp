package com.prmto.mova_movieapp.domain.use_case.get_tv_detail

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.data.models.detail.tv.toTvDetail
import com.prmto.mova_movieapp.domain.models.detail.TvDetail
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import com.prmto.mova_movieapp.presentation.util.UiText
import com.prmto.mova_movieapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetTvDetailUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {

    operator fun invoke(
        language: String,
        tvId: Int
    ): Flow<Resource<TvDetail>> {
        return flow {
            try {
                val response = remoteRepository.getTvDetail(language = language, tvId = tvId)
                val tvDetail = response.toTvDetail()
                emit(Resource.Success(data = tvDetail))
            } catch (e: IOException) {
                emit(Resource.Error(UiText.StringResource(R.string.internet_error)))
            } catch (e: HttpException) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
                Timber.e("Error", e)
            }
        }
    }
}