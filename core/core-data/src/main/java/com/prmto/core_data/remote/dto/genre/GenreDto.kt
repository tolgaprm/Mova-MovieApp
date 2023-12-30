package com.prmto.core_data.remote.dto.genre

data class GenreDto(
    val id: Int?,
    val name: String?
)

data class GenreListDto(
    val genres: List<GenreDto>?
)