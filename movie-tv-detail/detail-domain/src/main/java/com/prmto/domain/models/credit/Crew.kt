package com.prmto.domain.models.credit

data class Crew(
    val id: Int,
    val name: String,
    val originalName: String,
    val profilePath: String?,
    val department: String
)