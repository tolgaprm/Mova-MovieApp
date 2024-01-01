package com.prmto.data.remote.dto.detail.tv

import com.squareup.moshi.Json

data class CreatedByDto(
    @Json(name = "credit_id") val creditId: String?,
    val gender: Int?,
    val id: Int?,
    val name: String?,
    @Json(name = "profile_path") val profilePath: String?
)