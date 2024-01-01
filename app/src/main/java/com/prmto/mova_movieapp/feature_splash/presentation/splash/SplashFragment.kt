package com.prmto.mova_movieapp.feature_splash.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prmto.core_ui.util.collectFlow
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.feature_splash.presentation.splash.event.SplashEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectFlow(viewModel.eventFlow) { event ->
            when (event) {
                is SplashEvent.NavigateTo -> {
                    findNavController().navigate(event.directions)
                }

                is SplashEvent.UpdateAppLanguage -> {
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(
                            event.language
                        )
                    )
                }

                is SplashEvent.UpdateUiMode -> {
                    if (event.uiMode == AppCompatDelegate.MODE_NIGHT_YES) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        }
    }
}