package com.prmto.mova_movieapp.presentation.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.mova_movieapp.domain.use_case.get_language_iso_code.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.repository.FakeDataStoreOperations
import com.prmto.mova_movieapp.util.TestDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var viewModel: SplashViewModel


    @Before
    fun setup() {
        viewModel = SplashViewModel(
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(FakeDataStoreOperations()),
            dispatchers = TestDispatchers()
        )
    }

    @Test
    fun `after 2000 ms delay, check isNavigateToHomeFragment is true`() = runTest {
        val job = launch {
            viewModel.isNavigateToHomeFragment.test {
                val emission = awaitItem()
                assertThat(emission).isTrue()

                cancelAndConsumeRemainingEvents()
            }
        }
        viewModel.navigateToHomeFragment()
        job.join()
        job.cancel()

    }

}

