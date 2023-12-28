package com.prmto.mova_movieapp.feature_explore.presentation.filter_bottom_sheet.state

import com.prmto.mova_movieapp.core.domain.models.Category
import com.prmto.mova_movieapp.core.domain.models.Sort
import com.prmto.mova_movieapp.core.domain.models.genre.Genre

data class FilterBottomState(
    val categoryState: Category = Category.MOVIE,
    val checkedGenreIdsState: List<Int> = emptyList(),
    val checkedSortState: Sort = Sort.Popularity,
    val genreList: List<Genre> = listOf()
)