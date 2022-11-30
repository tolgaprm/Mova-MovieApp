package com.prmto.mova_movieapp.data.models.detail.tv

import com.squareup.moshi.Json

data class Network(
    val id: Int,
    @Json(name = "logo_path") val logoPath: String?,
    val name: String,
    @Json(name = "origin_country") val originCountry: String
)