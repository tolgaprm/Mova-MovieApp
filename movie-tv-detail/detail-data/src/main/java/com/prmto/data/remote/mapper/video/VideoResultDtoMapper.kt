package com.prmto.data.remote.mapper.video

import com.prmto.data.remote.dto.detail.video.VideoResultDto
import com.prmto.domain.models.detail.video.VideoResult

fun VideoResultDto.toVideoResult(): VideoResult {
    return VideoResult(
        id = id.orEmpty(),
        key = key.orEmpty(),
        site = site.orEmpty(),
        name = name.orEmpty(),
        type = type.orEmpty(),
        publishedAt = publishedAt.orEmpty()
    )
}