package com.prmto.mova_movieapp.domain.models

data class Genre(
    val id: Int,
    val name: String
)

data class GenreList(
    val genres: List<Genre>
)
