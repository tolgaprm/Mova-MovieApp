package com.prmto.mova_movieapp.feature_explore.presentation.event

import com.prmto.mova_movieapp.core.data.models.enums.Category
import com.prmto.mova_movieapp.core.data.models.enums.Sort


sealed class ExploreBottomSheetEvent {
    data class UpdateCategory(val checkedCategory: Category) : ExploreBottomSheetEvent()
    data class UpdateGenreList(val checkedList: List<Int>) : ExploreBottomSheetEvent()
    data class UpdateSort(val checkedSort: Sort) : ExploreBottomSheetEvent()
    object ResetFilterBottomState : ExploreBottomSheetEvent()
    object Apply : ExploreBottomSheetEvent()
}