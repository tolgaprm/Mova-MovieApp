package com.prmto.core_ui.base.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.prmto.core_ui.util.UiEvent

abstract class BaseFragmentWithUiEvent<VB : ViewBinding, VM : ViewModel>(
    inflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BaseFragment<VB, VM>(inflater = inflater) {

    fun handleUiEvent(
        listOfUiEvent: List<UiEvent>,
        onEventConsumed: () -> Unit
    ) {
        if (listOfUiEvent.isNotEmpty()) {
            when (val firstEvent = listOfUiEvent.first()) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(firstEvent.uiText)
                    onEventConsumed()
                }

                is UiEvent.NavigateTo -> {
                    findNavController().navigate(firstEvent.directions)
                    onEventConsumed()
                }

                UiEvent.PopBackStack -> {
                    findNavController().popBackStack()
                    onEventConsumed()
                }
            }
        }
    }
}