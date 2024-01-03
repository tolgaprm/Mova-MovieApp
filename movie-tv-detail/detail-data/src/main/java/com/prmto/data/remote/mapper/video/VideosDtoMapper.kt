package com.prmto.data.remote.mapper.video

import com.prmto.core_data.util.orZero
import com.prmto.data.remote.dto.detail.video.VideosDto
import com.prmto.domain.models.detail.video.Videos

fun VideosDto.toVideo(): Videos {
    return Videos(
        id = id.orZero(),
        result = videoResultDto?.map { it.toVideoResult() }.orEmpty()
    )
}