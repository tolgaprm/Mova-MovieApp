package com.prmto.domain.use_cases.movie.usecases

import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.handleResource
import com.prmto.domain.models.detail.video.Videos
import com.prmto.domain.repository.movie.MovieDetailRepository
import com.prmto.domain.use_cases.util.filterTrailerOrTeaserSortedByDescending
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