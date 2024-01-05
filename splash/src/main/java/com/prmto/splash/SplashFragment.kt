package com.prmto.splash

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import com.prmto.core_ui.base.fragment.BaseFragment
import com.prmto.core_ui.util.collectFlow
import com.prmto.splash.databinding.FragmentSplashBinding
import com.prmto.splash.event.SplashEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<
        FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::inflate
) {

    override val viewModel: SplashViewModel by viewModels()
    override fun onInitialize() {
        collectFlow(viewModel.eventFlow) { event ->
            when (event) {
                is SplashEvent.NavigateTo -> {
                    navigateToFlow(event.navigationFlow)
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