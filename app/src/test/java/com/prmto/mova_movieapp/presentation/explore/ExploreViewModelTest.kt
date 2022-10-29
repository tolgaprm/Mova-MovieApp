package com.prmto.mova_movieapp.presentation.explore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.mova_movieapp.data.models.enums.Category
import com.prmto.mova_movieapp.domain.use_case.get_locale.GetLocaleUseCase
import com.prmto.mova_movieapp.domain.use_case.get_movie_genre_list.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.domain.use_case.get_tv_genre_list.GetTvGenreListUseCase
import com.prmto.mova_movieapp.repository.FakeDataStoreOperations
import com.prmto.mova_movieapp.repository.FakeRemoteRepository
import com.prmto.mova_movieapp.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ExploreViewModelTest {

    private lateinit var viewModel: ExploreViewModel

    private lateinit var fakeRemoteRepository: FakeRemoteRepository
    private lateinit var fakeDataStoreOperations: FakeDataStoreOperations

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {

        fakeDataStoreOperations = FakeDataStoreOperations()
        fakeRemoteRepository = FakeRemoteRepository()

        viewModel = ExploreViewModel(
            tvGenreListUseCase = GetTvGenreListUseCase(fakeRemoteRepository),
            movieGenreListUseCase = GetMovieGenreListUseCase(fakeRemoteRepository),
            getLocaleUseCase = GetLocaleUseCase(fakeDataStoreOperations)
        )
    }


    @Test
    fun `get genre list by Tv Category and language tr, assert genreListWithLanguageTr`() =
        runTest {

            viewModel.setCategoryState(Category.TV)

            viewModel.setLocale("tr")

            viewModel.language.test {
                val language = awaitItem()
                viewModel.getGenreListByCategoriesState(language)

                cancelAndConsumeRemainingEvents()
            }

            viewModel.genreList.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(fakeRemoteRepository.tvGenreListLanguageTr.genres)

                cancelAndConsumeRemainingEvents()
            }

        }

    @Test
    fun `get genre list by Tv Category and language en, assert genreListWithLanguageEn`() =
        runTest {

            viewModel.setCategoryState(Category.TV)

            viewModel.setLocale("en")

            viewModel.language.test {
                val language = awaitItem()


                viewModel.getGenreListByCategoriesState(language)

                cancelAndConsumeRemainingEvents()
            }

            viewModel.genreList.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(fakeRemoteRepository.tvGenreListLanguageEn.genres)

                cancelAndConsumeRemainingEvents()
            }

        }

    @Test
    fun `get genre list by Movie Category and language en, assert genreListWithLanguageEn`() =
        runTest {

            viewModel.setLocale("en")

            viewModel.language.test {
                val language = awaitItem()


                viewModel.getGenreListByCategoriesState(language)

                cancelAndConsumeRemainingEvents()
            }

            viewModel.genreList.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(fakeRemoteRepository.movieGenreListLanguageEn.genres)

                cancelAndConsumeRemainingEvents()
            }

        }

    @Test
    fun `get genre list by Movie Category and language tr, assert genreListWithLanguageTr`() =
        runTest {

            viewModel.setLocale("tr")

            viewModel.language.test {
                val language = awaitItem()

                viewModel.getGenreListByCategoriesState(language)

                cancelAndConsumeRemainingEvents()
            }

            viewModel.genreList.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(fakeRemoteRepository.movieGenreListLanguageTr.genres)

                cancelAndConsumeRemainingEvents()
            }

        }

    @Test
    fun `update category status, check  is category updated, not reset selected genres if category is same, reset if not same`() =
        runTest {

            var checkedGenreIds = listOf(2, 1, 6)

            viewModel.setGenreList(checkedGenreIds)

            assertThat(checkedGenreIds).isEqualTo(getCheckedGenreIdsState())

            // Category state is Movie as default
            viewModel.setCategoryState(Category.MOVIE)

            // No reset selected Genre ids because category state is the same
            assertThat(checkedGenreIds).isEqualTo(getCheckedGenreIdsState())

            viewModel.setCategoryState(Category.TV)

            checkedGenreIds = emptyList()
            // reset selected Genre ids because category state is not the same
            assertThat(checkedGenreIds).isEqualTo(getCheckedGenreIdsState())
        }

    private fun getCheckedGenreIdsState(): List<Int> {

        var checkedGenreList = listOf<Int>()
        runTest {
            viewModel.filterBottomSheetState.test {
                val filterBottomState = awaitItem()
                checkedGenreList = filterBottomState.checkedGenreIdsState
                cancelAndConsumeRemainingEvents()
            }
        }
        return checkedGenreList
    }

}