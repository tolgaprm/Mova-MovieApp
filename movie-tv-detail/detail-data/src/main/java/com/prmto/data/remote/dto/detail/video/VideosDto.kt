package com.prmto.data.remote.dto.detail.video

import com.squareup.moshi.Json

data class VideosDto(
    val id: Int?,
    @Json(name = "results") val videoResultDto: List<VideoResultDto>?
)
