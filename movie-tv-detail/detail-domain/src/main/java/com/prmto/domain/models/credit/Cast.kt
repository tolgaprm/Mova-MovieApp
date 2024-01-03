package com.prmto.domain.models.credit

data class Cast(
    val id: Int,
    val name: String,
    val originalName: String,
    val profilePath: String?,
    val character: String,
)
