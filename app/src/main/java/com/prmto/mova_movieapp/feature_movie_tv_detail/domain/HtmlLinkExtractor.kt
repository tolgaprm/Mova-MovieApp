package com.prmto.mova_movieapp.feature_movie_tv_detail.domain

interface HtmlLinkExtractor {

    suspend fun getWatchProvidersLinks(
        url: String
    ): List<String>
}