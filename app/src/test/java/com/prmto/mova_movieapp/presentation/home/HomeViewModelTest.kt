package com.prmto.mova_movieapp.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import app.cash.turbine.timeout
import com.google.common.truth.Truth.assertThat
import com.prmto.mova_movieapp.domain.use_case.HomeUseCases
import com.prmto.mova_movieapp.domain.use_case.get_locale.GetLocaleUseCase
import com.prmto.mova_movieapp.domain.use_case.get_movie_genre_list.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.domain.use_case.get_now_playing_movies.GetNowPlayingMoviesUseCase
import com.prmto.mova_movieapp.domain.use_case.get_popular_movies.GetPopularMoviesUseCase
import com.prmto.mova_movieapp.domain.use_case.get_popular_tv_series.GetPopularTvSeries
import com.prmto.mova_movieapp.domain.use_case.get_top_rated_movies.GetTopRatedMoviesUseCase
import com.prmto.mova_movieapp.domain.use_case.get_top_rated_tv_series.GetTopRatedTvSeriesUseCase
import com.prmto.mova_movieapp.domain.use_case.get_tv_genre_list.GetTvGenreListUseCase
import com.prmto.mova_movieapp.repository.FakeDataStoreOperations
import com.prmto.mova_movieapp.repository.FakeNetworkConnectivityObserver
import com.prmto.mova_movieapp.repository.FakeRemoteRepository
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {

        val fakeDataStoreOperations = FakeDataStoreOperations()
        val fakeRemoteRepository = FakeRemoteRepository()
        val fakeNetworkConnectivityObserver = FakeNetworkConnectivityObserver()
        val savedStateHandle = mockk<SavedStateHandle>(relaxed = true)


        val homeUseCases = HomeUseCases(
            getMovieGenreList = GetMovieGenreListUseCase(fakeRemoteRepository),
            getTvGenreList = GetTvGenreListUseCase(fakeRemoteRepository),
            getNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCase(fakeRemoteRepository),
            getLocaleUseCase = GetLocaleUseCase(fakeDataStoreOperations),
            getPopularTvSeries = GetPopularTvSeries(fakeRemoteRepository),
            getPopularMoviesUseCase = GetPopularMoviesUseCase(fakeRemoteRepository),
            getTopRatedTvSeriesUseCase = GetTopRatedTvSeriesUseCase(fakeRemoteRepository),
            getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(fakeRemoteRepository)
        )

        homeViewModel = HomeViewModel(
            homeUseCases = homeUseCases,
            networkConnectivityObserver = fakeNetworkConnectivityObserver,
            savedStateHandle = savedStateHandle
        )
    }

    @Test
    fun `check that is language default value tr`() = runTest {
        homeViewModel.getLanguage().test {
            val language = awaitItem()
            assertThat(language).isEqualTo("tr")
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `update language value to 'us' and check is it updated`() = runTest {
        homeViewModel.setLanguage("us")

        homeViewModel.language.test {
            val language = awaitItem()
            assertThat(language).isEqualTo("us")
            cancelAndConsumeRemainingEvents()
        }

    }

}