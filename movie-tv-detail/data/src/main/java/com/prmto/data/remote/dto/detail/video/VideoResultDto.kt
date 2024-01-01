package com.prmto.data.remote.dto.detail.video

import com.squareup.moshi.Json

data class VideoResultDto(
    val id: String?,
    val iso_3166_1: String?,
    val iso_639_1: String?,
    val key: String?,
    val name: String?,
    val official: Boolean?,
    @Json(name = "published_at") val publishedAt: String?,
    val site: String?,
    val size: Int?,
    val type: String?,
)