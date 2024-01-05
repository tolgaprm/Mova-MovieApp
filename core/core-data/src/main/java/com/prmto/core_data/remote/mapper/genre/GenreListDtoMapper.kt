package com.prmto.core_data.remote.mapper.genre

import com.prmto.core_data.remote.dto.genre.GenreListDto
import com.prmto.core_data.util.orZero
import com.prmto.core_domain.models.genre.Genre
import com.prmto.core_domain.models.genre.GenreList

fun GenreListDto.toGenreList(): GenreList {
    val genres = genres?.map {
        Genre(
            id = it.id.orZero(),
            name = it.name.orEmpty()
        )
    }.orEmpty()

    return GenreList(genres)
}