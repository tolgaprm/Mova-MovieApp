package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.tv

import com.prmto.mova_movieapp.core.domain.util.handleResource
import com.prmto.mova_movieapp.core.util.Resource
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.Videos
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.tv.TvDetailRepository
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.util.filterTrailerOrTeaserSortedByDescending
import javax.inject.Inject

class GetTvVideosUseCase @Inject constructor(
    private val tvDetailRepository: TvDetailRepository
) {
    suspend operator fun invoke(tvId: Int, language: String): Resource<Videos> {

        return handleResource(
            resourceSupplier = {
                tvDetailRepository.getTvVideos(
                    tvSeriesId = tvId,
                    language = language.lowercase()
                )
            },
            mapper = { videos ->
                videos.filterTrailerOrTeaserSortedByDescending()
            }
        )
    }
}