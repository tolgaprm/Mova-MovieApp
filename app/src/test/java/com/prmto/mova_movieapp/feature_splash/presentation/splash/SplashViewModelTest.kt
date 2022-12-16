package com.prmto.mova_movieapp.feature_splash.presentation.splash

import androidx.appcompat.app.AppCompatDelegate
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.domain.use_case.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.core.domain.use_case.GetUIModeUseCase
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.repository.FakeDataStoreOperations
import com.prmto.mova_movieapp.core.repository.FakeNetworkConnectivityObserver
import com.prmto.mova_movieapp.feature_splash.presentation.splash.event.SplashEvent
import com.prmto.mova_movieapp.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
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
            getUIModeUseCase = GetUIModeUseCase(FakeDataStoreOperations()),
            networkConnectivityObserver = FakeNetworkConnectivityObserver()
        )
    }

    @Test
    fun `After get language iso code, check is eventFlow UpdateAppLanguage`() = runTest {
        delay(1000)
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
        delay(1000)
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
    fun `connectivityObserver, if connectivity is avaliable eventFlow navigateTo else networkError`() =
        runTest {
            delay(1000)
            val job = launch {
                viewModel.eventFlow.test {
                    assertThat(awaitItem()).isEqualTo(
                        SplashEvent.NetworkError(
                            UiText.StringResource(
                                R.string.internet_error
                            )
                        )
                    )
                    delay(500)
                    assertThat(awaitItem()).isEqualTo(
                        SplashEvent.NavigateTo(
                            SplashFragmentDirections.actionToHomeFragment()
                        )
                    )
                    cancelAndConsumeRemainingEvents()
                }
            }
            viewModel.observeNetwork()
            job.join()
            job.cancel()
        }


}

