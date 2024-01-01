package com.prmto.core_ui.base.viewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModelWithUiEvent<Event> : BaseViewModel() {

    private val _consumableViewEvents = MutableStateFlow<List<Event>>(emptyList())
    val consumableViewEvents: StateFlow<List<Event>> = _consumableViewEvents.asStateFlow()

    protected fun addConsumableViewEvent(uiEvent: Event) {
        _consumableViewEvents.update {
            it.plus(uiEvent)
        }
    }

    fun onEventConsumed() {
        val newConsumableViewEvents = consumableViewEvents.value.toMutableList()
        newConsumableViewEvents.removeFirstOrNull()
        _consumableViewEvents.update { newConsumableViewEvents }
    }
}