package com.prmto.mova_movieapp.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.data.models.enums.Categories
import com.prmto.mova_movieapp.domain.models.Genre
import com.prmto.mova_movieapp.domain.use_case.get_locale.GetLocaleUseCase
import com.prmto.mova_movieapp.domain.use_case.get_movie_genre_list.GetMovieGenreListUseCase
import com.prmto.mova_movieapp.domain.use_case.get_tv_genre_list.GetTvGenreListUseCase
import com.prmto.mova_movieapp.util.Constants.DEFAULT_LANGUAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val tvGenreListUseCase: GetTvGenreListUseCase,
    private val movieGenreListUseCase: GetMovieGenreListUseCase,
    private val getLocaleUseCase: GetLocaleUseCase
) : ViewModel() {


    private val _language = MutableStateFlow<String>(DEFAULT_LANGUAGE)
    val language: StateFlow<String> get() = _language

    private val _genreList = MutableStateFlow<List<Genre>>(emptyList())
    val genreList: StateFlow<List<Genre>> get() = _genreList

    private val _categoriesState = MutableStateFlow(Categories.MOVIE)
    val categoriesState: StateFlow<Categories> = _categoriesState

    private val _periodsList = MutableStateFlow<List<Genre>>(emptyList())
    val periodsList: StateFlow<List<Genre>> get() = _periodsList

    fun setCategoriesState(value: Categories) {
        _categoriesState.value = value
    }

    init {

        setupTimePeriods()

        viewModelScope.launch(Dispatchers.IO) {
            getLocale().collectLatest {
                _language.value = it
            }
        }


    }

    private fun getLocale(): Flow<String> {
        return getLocaleUseCase.invoke()
    }

    fun getGenreListByCategoriesState() {

        viewModelScope.launch {
            try {
                _genreList.value = if (categoriesState.value == Categories.TV) {
                    tvGenreListUseCase.invoke(_language.value.lowercase()).genres
                } else {
                    movieGenreListUseCase.invoke(language = _language.value).genres
                }
            } catch (e: Exception) {
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

        _periodsList.value = periods.mapIndexed { index, s ->
            Genre(id = index, name = s)
        }
    }
}


