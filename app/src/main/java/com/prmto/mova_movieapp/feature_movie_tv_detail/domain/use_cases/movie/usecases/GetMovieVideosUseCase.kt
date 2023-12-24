package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.movie.usecases

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.Videos
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.movie.MovieDetailRepository
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {

    suspend operator fun invoke(movieId: Int, language: String): Resource<Videos> {
        val resource = movieDetailRepository.getMovieVideos(
            movieId = movieId,
            language = language.lowercase()
        )

        return when (resource) {
            is Resource.Success -> {
                resource.data?.let { videos ->
                    val result = videos.copy(
                        result = videos.result.filter { it.isTypeTrailer() || it.isTypeTeaser() }
                            .sortedByDescending { it.isTypeTrailer() }
                    )
                    Resource.Success(data = result)
                } ?: Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
            }

            is Resource.Error -> {
                Resource.Error(uiText = UiText.StringResource(R.string.unknown_error))
            }
        }
    }
}