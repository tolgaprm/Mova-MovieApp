package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.Videos
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.DetailRepository
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetTvVideosUseCase @Inject constructor(
    private val repository: DetailRepository
) {
    suspend operator fun invoke(tvId: Int, language: String): Resource<Videos> {
        return try {
            val tvVideos = repository.getTvVideos(
                tvId = tvId,
                language = language.lowercase()
            )

            val result = tvVideos.copy(
                result = tvVideos.result.filter { it.isTypeTrailer() || it.isTypeTeaser() }
                    .sortedByDescending { it.isTypeTrailer() }
            )

            Resource.Success(data = result)
        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(R.string.internet_error))
        } catch (e: HttpException) {
            Timber.e(e.message())
            Resource.Error(UiText.StringResource(R.string.unknown_error))
        } catch (e: Exception) {
            Timber.e(e.localizedMessage?.toString())
            Resource.Error(UiText.StringResource(R.string.unknown_error))
        }
    }
}