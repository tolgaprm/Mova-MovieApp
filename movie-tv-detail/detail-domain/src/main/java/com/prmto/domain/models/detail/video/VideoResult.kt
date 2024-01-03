package com.prmto.domain.models.detail.video

import com.prmto.domain.util.Constants.TYPE_TEASER
import com.prmto.domain.util.Constants.TYPE_TRAILER

data class VideoResult(
    val id: String,
    val key: String,
    val site: String,
    val name: String,
    val type: String,
    val publishedAt: String
) {
    fun isTypeTrailer() = this.type == TYPE_TRAILER

    fun isTypeTeaser() = this.type == TYPE_TEASER
}