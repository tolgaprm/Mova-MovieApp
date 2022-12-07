package com.prmto.mova_movieapp.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.domain.use_case.get_language_iso_code.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.domain.use_case.update_country_iso_code.UpdateCountryIsoCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLanguageIsoCodeUseCase: GetLanguageIsoCodeUseCase,
    private val updateCountryIsoCodeUseCase: UpdateCountryIsoCodeUseCase
) : ViewModel() {


    private val _isNavigateToHomeFragment = MutableSharedFlow<Boolean>()
    val isNavigateToHomeFragment = _isNavigateToHomeFragment.asSharedFlow()

    fun navigateToHomeFragment() {
        viewModelScope.launch() {
            delay(2000)
            _isNavigateToHomeFragment.emit(true)
        }
    }

    fun updateCountryIsoCode(countryIsoCode: String) {
        viewModelScope.launch {
            updateCountryIsoCodeUseCase(countryIsoCode)
        }
    }

    fun getLanguageIsoCode(): Flow<String> {
        return getLanguageIsoCodeUseCase()
    }
}