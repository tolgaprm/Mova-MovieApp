package com.prmto.splash

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.fake.repository.FakeDataStoreOperations
import com.prmto.core_domain.use_case.languageIsoCode.GetLanguageIsoCodeUseCase
import com.prmto.core_domain.use_case.uIMode.GetUIModeUseCase
import com.prmto.core_testing.rules.MainDispatcherRule
import com.prmto.splash.event.SplashEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {
    private lateinit var viewModel: SplashViewModel
    private lateinit var dataStoreOperations: FakeDataStoreOperations

    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()

    @Before
    fun setUp() {
        dataStoreOperations = FakeDataStoreOperations()
        viewModel = SplashViewModel(
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(dataStoreOperations),
            getUIModeUseCase = GetUIModeUseCase(dataStoreOperations)
        )
    }


    @Test
    fun `getLanguageIsoCode, get language iso code, then emit UpdateAppLanguage Event`() =
        runTest {
            val defaultLanguageCode = dataStoreOperations.getLanguageIsoCode().first()

            viewModel.consumableViewEvents.test {
                assertThat(
                    awaitItem().first()
                ).isEqualTo(SplashEvent.UpdateAppLanguage(defaultLanguageCode))
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getUiMode, get language iso code, then emit UpdateAppLanguage Event`() =
        runTest {
            val defaultUiMode = dataStoreOperations.getUIMode().first()

            viewModel.consumableViewEvents.test {
                awaitItem() // First LanguageIsoCode event
                viewModel.onEventConsumed() // Consumed the Event
                assertThat(
                    awaitItem().first()
                ).isEqualTo(SplashEvent.UpdateUiMode(defaultUiMode!!))
                cancelAndIgnoreRemainingEvents()
            }
        }
}