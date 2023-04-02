package com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper

import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.detail.video.VideoResultDto
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.VideoResult

fun VideoResultDto.toVideoResult(): VideoResult {
    return VideoResult(
        id = id,
        key = key,
        site = site,
        name = name,
        type = type,
        publishedAt = publishedAt
    )
}