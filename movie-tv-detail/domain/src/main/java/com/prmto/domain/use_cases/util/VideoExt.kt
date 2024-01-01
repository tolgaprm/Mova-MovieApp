package com.prmto.domain.use_cases.util

import com.prmto.domain.models.detail.video.Videos

fun Videos.filterTrailerOrTeaserSortedByDescending() = this.copy(
    result = this.result.filter { it.isTypeTrailer() || it.isTypeTeaser() }
        .sortedByDescending { it.isTypeTrailer() }
)