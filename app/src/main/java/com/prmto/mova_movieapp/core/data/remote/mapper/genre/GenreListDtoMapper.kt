package com.prmto.mova_movieapp.core.data.remote.mapper.genre

import com.prmto.mova_movieapp.core.data.remote.dto.genre.GenreListDto
import com.prmto.mova_movieapp.core.data.util.orZero
import com.prmto.mova_movieapp.core.domain.models.genre.Genre
import com.prmto.mova_movieapp.core.domain.models.genre.GenreList

fun GenreListDto.toGenreList(): GenreList {
    val genres = genres?.map {
        Genre(
            id = it.id.orZero(),
            name = it.name.orEmpty()
        )
    }.orEmpty()

    return GenreList(genres)
}