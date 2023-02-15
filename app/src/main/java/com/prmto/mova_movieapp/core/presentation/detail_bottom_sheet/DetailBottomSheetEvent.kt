package com.prmto.mova_movieapp.core.presentation.detail_bottom_sheet

sealed class DetailBottomSheetEvent {
    object Close : DetailBottomSheetEvent()
    object Share : DetailBottomSheetEvent()
    object NavigateToDetailFragment : DetailBottomSheetEvent()
    object NavigateToLoginFragment : DetailBottomSheetEvent()
    object ClickedAddFavoriteList : DetailBottomSheetEvent()
    object ClickedAddWatchList : DetailBottomSheetEvent()
}