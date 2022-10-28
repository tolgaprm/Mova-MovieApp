package com.prmto.mova_movieapp.presentation.filter_bottom_sheet.state

import com.prmto.mova_movieapp.data.models.enums.Category
import com.prmto.mova_movieapp.data.models.enums.Sort

data class FilterBottomState(
    val categoryState: Category = Category.MOVIE,
    val checkedGenreIdsState: List<Int> = emptyList(),
    val checkedSortState: Sort = Sort.Popularity,
    val checkedPeriodId: Int = 0
)
