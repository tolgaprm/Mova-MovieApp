package com.prmto.core_domain.models.genre

data class Genre(
    val id: Int,
    val name: String
)

data class GenreList(
    val genres: List<Genre>
)
