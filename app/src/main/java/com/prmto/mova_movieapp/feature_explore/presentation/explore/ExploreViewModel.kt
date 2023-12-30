package com.prmto.mova_movieapp.feature_explore.presentation.explore

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.prmto.core_domain.models.Category
import com.prmto.core_domain.models.genre.Genre
import com.prmto.core_domain.models.isTv
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_domain.repository.ConnectivityObserver
import com.prmto.core_domain.repository.GenreRepository
import com.prmto.core_domain.repository.isAvaliable
import com.prmto.core_domain.util.Constants.DEFAULT_LANGUAGE
import com.prmto.core_domain.util.UiText
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.base.viewModel.BaseViewModelWithUiEvent
import com.prmto.mova_movieapp.core.presentation.util.UiEvent
import com.prmto.mova_movieapp.feature_explore.data.remote.dto.multisearch.SearchDto
import com.prmto.mova_movieapp.feature_explore.domain.model.MultiSearch
import com.prmto.mova_movieapp.feature_explore.domain.use_case.ExploreUseCases
import com.prmto.mova_movieapp.feature_explore.presentation.event.ExploreBottomSheetEvent
import com.prmto.mova_movieapp.feature_explore.presentation.event.ExploreFragmentEvent
import com.prmto.mova_movieapp.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreUseCases: ExploreUseCases,
    private val observeNetwork: ConnectivityObserver,
    private val genreRepository: GenreRepository
) : BaseViewModelWithUiEvent<UiEvent>() {
    private var languageState = DEFAULT_LANGUAGE

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    private var movieGenre = emptyList<Genre>()
    private var tvGenreList = emptyList<Genre>()

    private val _genreList = MutableStateFlow(emptyList<Genre>())
    val genreListState: StateFlow<List<Genre>> = _genreList.asStateFlow()

    private val _networkState = MutableStateFlow(ConnectivityObserver.Status.Unavaliable)
    val networkState: StateFlow<ConnectivityObserver.Status> = _networkState.asStateFlow()

    private val _filterBottomSheetState = MutableStateFlow(FilterBottomState())
    val filterBottomSheetState = _filterBottomSheetState.asStateFlow()

    init {
        viewModelScope.launch(handler) {
            collectNetworkState()
            collectLanguageIsoCode()
            getMovieGenreList()
            getTvGenreList()
        }
    }

    private fun collectNetworkState() {
        viewModelScope.launch {
            observeNetwork.observe().collectLatest { status ->
                _networkState.value = status
            }
        }
    }

    private fun collectLanguageIsoCode() {
        viewModelScope.launch {
            exploreUseCases.getLanguageIsoCodeUseCase().collectLatest { language ->
                languageState = language
                getGenreListByCategoriesState()
            }
        }
    }

    fun isNetworkAvaliable(): Boolean {
        return networkState.value.isAvaliable()
    }

    fun multiSearch(query: String): Flow<PagingData<MultiSearch>> {
        return if (query.isNotEmpty()) {
            exploreUseCases.multiSearchUseCase(query = query, language = languageState)
                .cachedIn(viewModelScope)
        } else {
            flow<PagingData<MultiSearch>> { PagingData.empty<SearchDto>() }.cachedIn(viewModelScope)
        }
    }

    fun discoverMovie(): Flow<PagingData<Movie>> {
        return exploreUseCases.discoverMovieUseCase(
            language = languageState,
            filterBottomState = filterBottomSheetState.value
        ).cachedIn(viewModelScope)
    }

    fun discoverTv(): Flow<PagingData<TvSeries>> {
        return exploreUseCases.discoverTvUseCase(
            language = languageState,
            filterBottomState = filterBottomSheetState.value
        ).cachedIn(viewModelScope)
    }


    fun onEventExploreFragment(event: ExploreFragmentEvent) {
        when (event) {
            is ExploreFragmentEvent.MultiSearch -> {
                _query.value = event.query
                if (query.value.isNotEmpty()) {
                    _filterBottomSheetState.update { it.copy(categoryState = Category.SEARCH) }
                } else {
                    _filterBottomSheetState.update { it.copy(categoryState = Category.MOVIE) }
                }
            }

            is ExploreFragmentEvent.RemoveQuery -> {
                _query.value = ""
            }
        }
    }

    fun onEventBottomSheet(event: ExploreBottomSheetEvent) {
        when (event) {
            is ExploreBottomSheetEvent.UpdateCategory -> {
                _filterBottomSheetState.update { it.copy(categoryState = event.checkedCategory) }
                getGenreListByCategoriesState()
                resetSelectedGenreIdsState()
            }

            is ExploreBottomSheetEvent.UpdateGenreList -> {
                resetSelectedGenreIdsState()
                _filterBottomSheetState.update { it.copy(checkedGenreIdsState = event.checkedList) }
            }

            is ExploreBottomSheetEvent.UpdateSort -> {
                _filterBottomSheetState.update { it.copy(checkedSortState = event.checkedSort) }
            }

            is ExploreBottomSheetEvent.ResetFilterBottomState -> {
                _filterBottomSheetState.value = FilterBottomState()
            }

            is ExploreBottomSheetEvent.Apply -> {
                addConsumableViewEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun resetSelectedGenreIdsState() {
        _filterBottomSheetState.update { it.copy(checkedGenreIdsState = emptyList()) }
    }

    private fun getGenreListByCategoriesState() {
        viewModelScope.launch(handler) {
            try {
                if (_filterBottomSheetState.value.categoryState.isTv()) {
                    _genreList.update { tvGenreList }
                } else {
                    _genreList.update { movieGenre }
                }
            } catch (e: Exception) {
                addConsumableViewEvent(
                    UiEvent.ShowSnackbar(UiText.StringResource(R.string.internet_error))
                )
            }
        }
    }

    private fun getMovieGenreList() {
        viewModelScope.launch(handler) {
            movieGenre = genreRepository.getMovieGenreList(languageState).genres
        }
    }

    private fun getTvGenreList() {
        viewModelScope.launch(handler) {
            tvGenreList = genreRepository.getTvGenreList(languageState).genres
        }
    }
}