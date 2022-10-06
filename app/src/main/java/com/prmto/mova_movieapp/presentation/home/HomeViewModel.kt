package com.prmto.mova_movieapp.presentation.home

import androidx.lifecycle.ViewModel
import com.prmto.mova_movieapp.domain.use_case.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    val nowPlayingMovies = homeUseCases.getNowPlayingMoviesUseCase
}