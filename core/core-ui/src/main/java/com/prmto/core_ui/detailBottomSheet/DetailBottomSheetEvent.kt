package com.prmto.core_ui.detailBottomSheet

sealed class DetailBottomSheetEvent {
    object Close : DetailBottomSheetEvent()
    object Share : DetailBottomSheetEvent()
    object NavigateToDetailFragment : DetailBottomSheetEvent()

    object ClickedAddFavoriteList : DetailBottomSheetEvent()
    object ClickedAddWatchList : DetailBottomSheetEvent()
}