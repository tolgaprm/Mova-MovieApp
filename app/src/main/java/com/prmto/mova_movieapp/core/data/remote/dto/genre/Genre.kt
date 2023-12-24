package com.prmto.mova_movieapp.core.data.remote.dto.genre

data class Genre(
    val id: Int,
    val name: String
)

data class GenreList(
    val genres: List<Genre>
)
