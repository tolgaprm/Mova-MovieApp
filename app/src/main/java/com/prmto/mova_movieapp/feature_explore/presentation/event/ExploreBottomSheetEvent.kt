package com.prmto.mova_movieapp.feature_explore.presentation.event

import com.prmto.core_domain.models.Category
import com.prmto.core_domain.models.Sort


sealed class ExploreBottomSheetEvent {
    data class UpdateCategory(val checkedCategory: Category) : ExploreBottomSheetEvent()
    data class UpdateGenreList(val checkedList: List<Int>) : ExploreBottomSheetEvent()
    data class UpdateSort(val checkedSort: Sort) : ExploreBottomSheetEvent()
    object ResetFilterBottomState : ExploreBottomSheetEvent()
    object Apply : ExploreBottomSheetEvent()
}