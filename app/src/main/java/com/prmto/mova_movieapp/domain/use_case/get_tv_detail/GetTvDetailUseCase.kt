package com.prmto.mova_movieapp.domain.use_case.get_tv_detail

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.data.models.detail.tv.toTvDetail
import com.prmto.mova_movieapp.domain.models.detail.TvDetail
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import com.prmto.mova_movieapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
                emit(Resource.Loading())
                val response = remoteRepository.getTvDetail(language = language, tvId = tvId)
                val tvDetail = response.toTvDetail()

                emit(Resource.Success(data = tvDetail))

            } catch (e: HttpException) {
                emit(Resource.Error(errorRes = R.string.internet_error))
            } catch (e: Exception) {
                emit(Resource.Error(errorRes = R.string.error))
                Timber.e("Error", e)
            }
        }
    }
}