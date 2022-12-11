package com.prmto.mova_movieapp.presentation.splash

import androidx.appcompat.app.AppCompatDelegate
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.mova_movieapp.domain.use_case.get_language_iso_code.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.domain.use_case.get_ui_mode.GetUIModeUseCase
import com.prmto.mova_movieapp.repository.FakeDataStoreOperations
import com.prmto.mova_movieapp.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SplashViewModel

    @Before
    fun setup() {
        viewModel = SplashViewModel(
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(FakeDataStoreOperations()),
            getUIModeUseCase = GetUIModeUseCase(FakeDataStoreOperations())
        )
    }

    @Test
    fun `After get language iso code, check is eventFlow UpdateAppLanguage`() = runTest {
        val job = launch {
            viewModel.eventFlow.test {
                assertThat(awaitItem()).isEqualTo(SplashEvent.UpdateAppLanguage("tr"))
                cancelAndConsumeRemainingEvents()
            }
        }
        viewModel.getLanguageIsoCode()
        job.join()
        job.cancel()
    }

    @Test
    fun `After get uiMode, check is eventFlow UpdateUiMode`() = runTest {
        val job = launch {
            viewModel.eventFlow.test {
                assertThat(awaitItem()).isEqualTo(SplashEvent.UpdateUiMode(AppCompatDelegate.MODE_NIGHT_NO))
                cancelAndConsumeRemainingEvents()
            }
        }
        viewModel.getUiMode()
        job.join()
        job.cancel()
    }

    @Test
    fun `After all collect events, check is eventFlow NavigateTo`() = runTest {
        val job = launch {
            viewModel.eventFlow.test {
                assertThat(awaitItem()).isEqualTo(SplashEvent.NavigateTo(SplashFragmentDirections.actionToHomeFragment()))
                cancelAndConsumeRemainingEvents()
            }
        }
        viewModel.navigateToHomeFragment()
        job.join()
        job.cancel()
    }


}

