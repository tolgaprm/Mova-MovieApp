package com.prmto.mova_movieapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.domain.repository.DataStoreOperations
import com.prmto.mova_movieapp.domain.use_case.DetailUseCases
import com.prmto.mova_movieapp.presentation.util.HandleUtils
import com.prmto.mova_movieapp.util.Constants.DEFAULT_LANGUAGE
import com.prmto.mova_movieapp.util.Constants.DETAIL_DEFAULT_ID
import com.prmto.mova_movieapp.util.Constants.HOUR_KEY
import com.prmto.mova_movieapp.util.Constants.MINUTES_KEY
import com.prmto.mova_movieapp.util.Constants.MOVIE_DETAIL_ID
import com.prmto.mova_movieapp.util.Constants.TV_DETAIL_ID
import com.prmto.mova_movieapp.util.Constants.TV_SERIES_STATUS_ENDED
import com.prmto.mova_movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCases: DetailUseCases,
    private val dataStoreOperations: DataStoreOperations,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _detailState = MutableStateFlow<DetailState>(DetailState())
    val detailState: StateFlow<DetailState> = _detailState.asStateFlow()

    private val _languageIsoCode = MutableStateFlow(DEFAULT_LANGUAGE)
    val languageIsoCode: StateFlow<String> = _languageIsoCode.asStateFlow()

    init {
        viewModelScope.launch {
            _languageIsoCode.value = dataStoreOperations.getLanguageIsoCode().first()
        }
    }

    val movieDetailId = savedStateHandle.getStateFlow<Int>(
        MOVIE_DETAIL_ID,
        DETAIL_DEFAULT_ID
    )

    val tvDetailId = savedStateHandle.getStateFlow<Int>(
        TV_DETAIL_ID,
        DETAIL_DEFAULT_ID
    )

    fun setMovieDetailId(movieId: Int) {
        savedStateHandle[MOVIE_DETAIL_ID] = movieId
        setDefaultTvId()
    }

    fun setTvDetailId(tvId: Int) {
        savedStateHandle[TV_DETAIL_ID] = tvId
        setDefaultMovieId()
    }

    private fun setDefaultMovieId() {
        savedStateHandle[MOVIE_DETAIL_ID] = DETAIL_DEFAULT_ID
    }

    private fun setDefaultTvId() {
        savedStateHandle[TV_DETAIL_ID] = DETAIL_DEFAULT_ID
    }

    fun getMovieDetail() {
        viewModelScope.launch {
            detailUseCases.movieDetailUseCase(
                language = _languageIsoCode.value,
                movieId = movieDetailId.value
            ).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _detailState.value = DetailState(loading = true)
                    }
                    is Resource.Error -> {
                        _detailState.value = DetailState(errorId = resource.errorRes)
                    }
                    is Resource.Success -> {
                        val movieDetail = resource.data
                        movieDetail?.let {
                            it.ratingValue = calculateRatingBarValue(voteAverage = it.voteAverage)
                            it.convertedRuntime = convertRuntime(it.runtime)
                        }
                        _detailState.value = DetailState(movieDetail = resource.data)
                    }
                }
            }
        }
    }

    fun getTvDetail() {
        viewModelScope.launch {
            detailUseCases.tvDetailUseCase(
                language = _languageIsoCode.value,
                tvId = tvDetailId.value
            ).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _detailState.value = DetailState(loading = true)
                    }
                    is Resource.Error -> {
                        _detailState.value = DetailState(errorId = resource.errorRes)
                    }
                    is Resource.Success -> {
                        val tvDetail = resource.data
                        tvDetail?.let {
                            it.ratingValue = calculateRatingBarValue(voteAverage = it.voteAverage)
                            it.releaseDate = handleTvSeriesReleaseDate(
                                firstAirDate = it.firstAirDate,
                                lastAirDate = it.lastAirDate,
                                status = it.status
                            )
                        }
                        _detailState.value = DetailState(tvDetail = resource.data)
                    }
                }
            }
        }
    }

    private fun calculateRatingBarValue(voteAverage: Double): Float {
        return ((voteAverage * 5) / 10).toFloat()
    }

    private fun convertRuntime(runtime: Int?): Map<String, String> {
        runtime?.let {
            val hour = runtime / 60
            val minutes = (runtime % 60)
            return mapOf(
                HOUR_KEY to hour.toString(),
                MINUTES_KEY to minutes.toString()
            )
        } ?: return emptyMap()
    }

    private fun handleTvSeriesReleaseDate(
        firstAirDate: String,
        lastAirDate: String,
        status: String
    ): String {
        val firstAirDateValue = HandleUtils.convertToYearFromDate(firstAirDate)
        return if (status == TV_SERIES_STATUS_ENDED) {
            val lastAirDateValue = HandleUtils.convertToYearFromDate(lastAirDate)
            "${firstAirDateValue}-${lastAirDateValue}"
        } else {
            "$firstAirDateValue-"
        }
    }
}