package com.prmto.domain.use_cases.tv

import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.handleResource
import com.prmto.domain.models.detail.video.Videos
import com.prmto.domain.repository.tv.TvDetailRepository
import com.prmto.domain.use_cases.util.filterTrailerOrTeaserSortedByDescending
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