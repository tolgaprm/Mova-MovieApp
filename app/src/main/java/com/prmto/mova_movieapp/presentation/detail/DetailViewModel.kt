package com.prmto.mova_movieapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.mova_movieapp.domain.repository.DataStoreOperations
import com.prmto.mova_movieapp.domain.use_case.DetailUseCases
import com.prmto.mova_movieapp.util.Constants.DEFAULT_LANGUAGE
import com.prmto.mova_movieapp.util.Constants.DETAIL_DEFAULT_ID
import com.prmto.mova_movieapp.util.Constants.MOVIE_DETAIL_ID
import com.prmto.mova_movieapp.util.Constants.TV_DETAIL_ID
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

    private val _languageIsoCode = MutableStateFlow<String>(DEFAULT_LANGUAGE)
    val languageIsoCode: StateFlow<String> = _languageIsoCode.asStateFlow()

    val movieDetailId = savedStateHandle.getStateFlow<Int>(
        MOVIE_DETAIL_ID,
        DETAIL_DEFAULT_ID
    )

    val tvDetailId = savedStateHandle.getStateFlow<Int>(
        TV_DETAIL_ID,
        DETAIL_DEFAULT_ID
    )

    init {
        viewModelScope.launch {
            _languageIsoCode.value = dataStoreOperations.getLanguageIsoCode().first()
        }
    }


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
            ).collect {
                when (it) {
                    is Resource.Loading -> {
                        _detailState.value = DetailState(loading = true)
                    }
                    is Resource.Error -> {
                        _detailState.value = DetailState(errorId = it.errorRes)
                    }
                    is Resource.Success -> {
                        _detailState.value = DetailState(movieDetail = it.data)
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
            ).collect {
                when (it) {
                    is Resource.Loading -> {
                        _detailState.value = DetailState(loading = true)
                    }
                    is Resource.Error -> {
                        _detailState.value = DetailState(errorId = it.errorRes)
                    }
                    is Resource.Success -> {
                        _detailState.value = DetailState(tvDetail = it.data)
                    }
                }
            }
        }
    }

}