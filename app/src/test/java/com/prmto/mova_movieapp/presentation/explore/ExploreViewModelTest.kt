package com.prmto.mova_movieapp.presentation.explore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.mova_movieapp.data.models.enums.Category
import com.prmto.mova_movieapp.data.models.enums.Sort
import com.prmto.mova_movieapp.domain.models.Period
import com.prmto.mova_movieapp.domain.use_case.ExploreUseCases
import com.prmto.mova_movieapp.domain.use_case.discover_movie.DiscoverMovieUseCase
import com.prmto.mova_movieapp.domain.use_case.discover_tv.DiscoverTvUseCase
import com.prmto.mova_movieapp.domain.use_case.get_language_iso_code.GetLanguageIsoCodeUseCase
import com.prmto.mova_movieapp.domain.use_case.get_movie_genre_list.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.domain.use_case.get_tv_genre_list.GetTvGenreListUseCase
import com.prmto.mova_movieapp.presentation.filter_bottom_sheet.state.FilterBottomState
import com.prmto.mova_movieapp.repository.FakeDataStoreOperations
import com.prmto.mova_movieapp.repository.FakeRemoteRepository
import com.prmto.mova_movieapp.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

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

        val exploreUseCase = ExploreUseCases(
            tvGenreListUseCase = GetTvGenreListUseCase(fakeRemoteRepository),
            movieGenreListUseCase = GetMovieGenreListUseCase(fakeRemoteRepository),
            getLanguageIsoCodeUseCase = GetLanguageIsoCodeUseCase(fakeDataStoreOperations),
            discoverTvUseCase = DiscoverTvUseCase(fakeRemoteRepository),
            discoverMovieUseCase = DiscoverMovieUseCase(fakeRemoteRepository)
        )


        viewModel = ExploreViewModel(
            exploreUseCases = exploreUseCase
        )
    }


    @Test
    fun `language state when initialized viewModel then assert language is tr`() = runTest {
        viewModel.language.test {
            assertThat(awaitItem()).isEqualTo("tr")
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `get genre list by Tv Category and language tr, check genreListWithLanguageTr`() =
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
    fun `period state when initialized viewModel then check list years from current year to 1985`() =
        runTest {
            viewModel.periodState.test {
                assertThat(awaitItem()).isEqualTo(listFromCurrentYearTo1985())
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `get genre list by Tv Category and language en, check genreListWithLanguageEn`() =
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
    fun `get genre list by Movie Category and language en, check genreListWithLanguageEn`() =
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
    fun `get genre list by Movie Category and language tr, check genreListWithLanguageTr`() =
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


    @Test
    fun `checkedSortState, check the  of the sortState default value is Popularity then update sort state, check is the updated`() =
        runTest {
            // SortState is default Popularity
            viewModel.filterBottomSheetState.test {
                val sortState = awaitItem().checkedSortState
                assertThat(sortState).isEqualTo(Sort.Popularity)
                cancelAndConsumeRemainingEvents()
            }

            viewModel.setCheckedSortState(Sort.LatestRelease)

            viewModel.filterBottomSheetState.test {
                val sortState = awaitItem().checkedSortState
                assertThat(sortState).isEqualTo(Sort.LatestRelease)
                cancelAndConsumeRemainingEvents()
            }

        }

    @Test
    fun `checked period state, set checked period state then check is the`() = runTest {

        val checkedPeriodId = 3

        viewModel.setCheckedPeriods(checkedPeriodId)

        viewModel.filterBottomSheetState.test {
            val checkedPeriodState = awaitItem().checkedPeriodId
            assertThat(checkedPeriodState).isEqualTo(checkedPeriodId)
            cancelAndConsumeRemainingEvents()
        }

    }

    @Test
    fun `filter bottom state, reset bottom state, check default bottom state`() = runTest {
        viewModel.setCategoryState(Category.TV)
        viewModel.setCheckedPeriods(3)
        viewModel.setGenreList(listOf(3, 5, 6, 7, 9))
        viewModel.setLocale("tr")
        viewModel.resetFilterBottomState()

        viewModel.filterBottomSheetState.test {
            val filterState = awaitItem()
            assertThat(filterState).isEqualTo(FilterBottomState())
        }

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

    private fun listFromCurrentYearTo1985(): List<Period> {

        val periods = mutableListOf<String>()
        val periodList = mutableListOf<Period>()

        val formatter = SimpleDateFormat("y", Locale.getDefault())
        val calendar = Calendar.getInstance()
        val currentYear = formatter.format(calendar.time).toInt()
        var year = currentYear

        periods.add("All Periods")
        repeat(35) {
            periods.add(year.toString())
            year--
        }

        periods.mapIndexed { index, s ->
            periodList.add(Period(index, s))
        }

        return periodList
    }

}