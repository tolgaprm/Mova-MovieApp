package com.prmto.mova_movieapp.feature_movie_tv_detail.data.link_extractor

import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.link_extractor.BaseLinkExtractor

class RentProviderLinkExtractor : BaseLinkExtractor() {
    override val parentElements = "h3:contains(Rent)"
}