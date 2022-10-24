package com.prmto.mova_movieapp.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.domain.use_case.update_current_locale.UpdateLocalCurrentUseCase
import com.prmto.mova_movieapp.util.Constants.DEFAULT_LANGUAGE
import com.prmto.mova_movieapp.util.Constants.supportedLanguages
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
            if (locale !in supportedLanguages) {
                updateLocalCurrentUseCase(locale = DEFAULT_LANGUAGE)
            } else {
                updateLocalCurrentUseCase(locale = locale)
            }

        }
    }
}