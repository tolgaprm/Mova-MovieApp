package com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.video

import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.VideoResult
import com.squareup.moshi.Json

data class VideoResultDto(
    val id: String,
    val iso_3166_1: String,
    val iso_639_1: String,
    val key: String,
    val name: String,
    val official: Boolean,
    @Json(name = "published_at") val publishedAt: String,
    val site: String,
    val size: Int,
    val type: String
) {
    fun toVideoResult(): VideoResult {
        return VideoResult(
            id = id,
            key = key,
            site = site,
            name = name,
            type = type,
            publishedAt = publishedAt
        )
    }
}

