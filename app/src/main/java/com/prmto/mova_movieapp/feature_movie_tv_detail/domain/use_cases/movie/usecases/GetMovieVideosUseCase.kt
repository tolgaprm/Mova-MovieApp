package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.movie.usecases

import com.prmto.mova_movieapp.core.domain.util.handleResource
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.Videos
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.movie.MovieDetailRepository
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.util.filterTrailerOrTeaserSortedByDescending
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {

    suspend operator fun invoke(movieId: Int, language: String): Resource<Videos> {
        return handleResource(
            resourceSupplier = {
                movieDetailRepository.getMovieVideos(
                    movieId = movieId,
                    language = language.lowercase()
                )
            },
            mapper = { videos ->
                videos.filterTrailerOrTeaserSortedByDescending()
            }
        )
    }
}