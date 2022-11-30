package com.prmto.mova_movieapp.data.models.detail

import com.squareup.moshi.Json

data class ProductionCompany(
    val id: Int,
    @Json(name = "logo_path") val logoPath: String?,
    val name: String,
    @Json(name = "origin_country") val originCountry: String
)