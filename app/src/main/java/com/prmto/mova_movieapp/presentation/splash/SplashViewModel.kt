package com.prmto.mova_movieapp.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.domain.use_case.update_current_locale.UpdateLocalCurrentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val updateLocalCurrentUseCase: UpdateLocalCurrentUseCase,
) : ViewModel() {

    fun updateLocale(locale: String) {
        viewModelScope.launch(Dispatchers.IO)
        {
            updateLocalCurrentUseCase(locale = locale)
        }
    }
}