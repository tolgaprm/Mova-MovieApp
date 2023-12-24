package com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.mapper.video

import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.dto.detail.video.VideoResultDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.VideoResult

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