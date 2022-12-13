package com.prmto.mova_movieapp.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.prmto.mova_movieapp.domain.use_case.HomeUseCases
import com.prmto.mova_movieapp.domain.use_case.get_language_iso_code.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.domain.use_case.get_movie_genre_list.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.domain.use_case.get_now_playing_movies.GetNowPlayingMoviesUseCase
import com.prmto.mova_movieapp.domain.use_case.get_popular_movies.GetPopularMoviesUseCase
import com.prmto.mova_movieapp.domain.use_case.get_popular_tv_series.GetPopularTvSeries
import com.prmto.mova_movieapp.domain.use_case.get_top_rated_movies.GetTopRatedMoviesUseCase
import com.prmto.mova_movieapp.domain.use_case.get_top_rated_tv_series.GetTopRatedTvSeriesUseCase
import com.prmto.mova_movieapp.domain.use_case.get_tv_genre_list.GetTvGenreListUseCase
import com.prmto.mova_movieapp.domain.use_case.update_current_language_iso_code.UpdateLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.repository.FakeDataStoreOperations
import com.prmto.mova_movieapp.repository.FakeNetworkConnectivityObserver
import com.prmto.mova_movieapp.repository.FakeRemoteRepository
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

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
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(fakeDataStoreOperations),
            getPopularTvSeries = GetPopularTvSeries(fakeRemoteRepository),
            getPopularMoviesUseCase = GetPopularMoviesUseCase(fakeRemoteRepository),
            getTopRatedTvSeriesUseCase = GetTopRatedTvSeriesUseCase(fakeRemoteRepository),
            getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(fakeRemoteRepository),
            updateLanguageIsoCodeUseCase = UpdateLanguageIsoCodeUseCase(fakeDataStoreOperations)
        )

        homeViewModel = HomeViewModel(
            homeUseCases = homeUseCases,
            networkConnectivityObserver = fakeNetworkConnectivityObserver
        )
    }

}