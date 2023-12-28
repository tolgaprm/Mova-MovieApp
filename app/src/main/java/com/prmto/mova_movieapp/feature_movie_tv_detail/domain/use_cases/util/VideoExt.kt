package com.prmto.mova_movieapp.feature_movie_tv_detail.domain.use_cases.util

import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.Videos

fun Videos.filterTrailerOrTeaserSortedByDescending() = this.copy(
    result = this.result.filter { it.isTypeTrailer() || it.isTypeTeaser() }
        .sortedByDescending { it.isTypeTrailer() }
)