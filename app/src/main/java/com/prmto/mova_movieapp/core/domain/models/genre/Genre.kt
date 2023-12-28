package com.prmto.mova_movieapp.core.domain.models.genre

data class Genre(
    val id: Int,
    val name: String
)

data class GenreList(
    val genres: List<Genre>
)
