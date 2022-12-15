package com.prmto.mova_movieapp.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.prmto.mova_movieapp.data.models.enums.Category
import com.prmto.mova_movieapp.data.models.enums.Sort
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.domain.models.Period
import com.prmto.mova_movieapp.domain.use_case.ExploreUseCases
import com.prmto.mova_movieapp.presentation.filter_bottom_sheet.state.FilterBottomState
import com.prmto.mova_movieapp.util.Constants.DEFAULT_LANGUAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class ExploreViewModel @Inject constructor(
    val exploreUseCases: ExploreUseCases
) : ViewModel() {


    private val _language = MutableStateFlow<String>(DEFAULT_LANGUAGE)
    val language = _language.asStateFlow()

    private val _genreList =
        MutableStateFlow<List<com.prmto.mova_movieapp.data.models.Genre>>(emptyList())
    val genreList = _genreList.asStateFlow()

    private val _filterBottomSheetState = MutableStateFlow(FilterBottomState())
    val filterBottomSheetState = _filterBottomSheetState.asStateFlow()

    private val _periodState = MutableStateFlow<List<Period>>(emptyList())
    val periodState = _periodState.asStateFlow()

    private val _isDownloadGenreOptions = MutableSharedFlow<Boolean>()
    val isDownloadGenreOptions = _isDownloadGenreOptions.asSharedFlow()


    init {
        setupTimePeriods()
    }


    fun discoverMovie(): Flow<PagingData<Movie>> {
        return exploreUseCases.discoverMovieUseCase(
            language.value,
            filterBottomState = filterBottomSheetState.value
        )
    }

    fun setCategoryState(newCategory: Category) {
        if (newCategory == _filterBottomSheetState.value.categoryState) {
            return
        }
        _filterBottomSheetState.update {
            it.copy(
                categoryState = newCategory
            )
        }
        resetSelectedGenreIdsState()

    }

    private fun resetSelectedGenreIdsState() {
        _filterBottomSheetState.update {
            it.copy(
                checkedGenreIdsState = emptyList()
            )
        }
    }

    fun setGenreList(checkedIds: List<Int>) {

        resetSelectedGenreIdsState()

        _filterBottomSheetState.update {
            it.copy(
                checkedGenreIdsState = checkedIds
            )
        }
    }

    fun setCheckedSortState(checkedSort: Sort) {
        _filterBottomSheetState.update {
            it.copy(
                checkedSortState = checkedSort
            )
        }
    }

    fun setCheckedPeriods(checkedPeriodId: Int) {
        _filterBottomSheetState.update {
            it.copy(
                checkedPeriodId = checkedPeriodId
            )
        }
    }

    fun resetFilterBottomState() {
        _filterBottomSheetState.update {
            FilterBottomState()
        }
    }

    fun setLocale(locale: String) {
        _language.value = locale
    }

    fun getGenreListByCategoriesState(language: String) {

        viewModelScope.launch {
            try {
                _genreList.value =
                    if (_filterBottomSheetState.value.categoryState == Category.TV) {
                        exploreUseCases.tvGenreListUseCase(language).genres
                    } else {
                        exploreUseCases.movieGenreListUseCase(language).genres
                    }
            } catch (e: Exception) {
                _isDownloadGenreOptions.emit(true)
                Timber.e("Didn't download genreList $e")
            }
        }

    }


    private fun setupTimePeriods() {

        val periods = mutableListOf<String>()

        val formatter = SimpleDateFormat("y", Locale.getDefault())
        val calendar = Calendar.getInstance()


        var year = formatter.format(calendar.time).toInt()

        periods.add("All Periods")
        repeat(35) {
            periods.add(year.toString())
            year--
        }

        _periodState.value = periods.mapIndexed { index, s ->
            Period(id = index, time = s)
        }

    }

}



