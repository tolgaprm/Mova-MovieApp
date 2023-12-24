package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.video

import com.prmto.mova_movieapp.core.data.util.orZero
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.detail.video.VideosDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.Videos

fun VideosDto.toVideo(): Videos {
    return Videos(
        id = id.orZero(),
        result = videoResultDto?.map { it.toVideoResult() }.orEmpty()
    )
}