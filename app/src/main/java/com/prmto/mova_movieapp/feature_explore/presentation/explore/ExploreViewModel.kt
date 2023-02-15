package com.prmto.mova_movieapp.feature_explore.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.dto.Genre
import com.prmto.mova_movieapp.core.data.models.enums.Category
import com.prmto.mova_movieapp.core.data.models.enums.isTv
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.domain.repository.ConnectivityObserver
import com.prmto.mova_movieapp.core.domain.repository.isAvaliable
import com.prmto.mova_movieapp.core.presentation.util.UiEvent
import com.prmto.mova_movieapp.core.presentation.util.UiText
import com.prmto.mova_movieapp.core.util.Constants.DEFAULT_LANGUAGE
import com.prmto.mova_movieapp.feature_explore.data.dto.SearchDto
import com.prmto.mova_movieapp.feature_explore.domain.use_case.ExploreUseCases
import com.prmto.mova_movieapp.feature_explore.presentation.event.ExploreBottomSheetEvent
import com.prmto.mova_movieapp.feature_explore.presentation.event.ExploreFragmentEvent
import com.prmto.mova_movieapp.feature_explore.presentation.explore.event.ExploreAdapterLoadStateEvent
import com.prmto.mova_movieapp.feature_explore.presentation.explore.state.ExplorePagingAdapterLoadState
import com.prmto.mova_movieapp.feature_explore.presentation.filter_bottom_sheet.state.FilterBottomState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val exploreUseCases: ExploreUseCases,
    private val observeNetwork: ConnectivityObserver
) : ViewModel() {


    private val _language = MutableStateFlow(DEFAULT_LANGUAGE)
    val language = _language.asStateFlow()

    private val _genreList = MutableStateFlow<List<Genre>>(emptyList())
    val genreList = _genreList.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    private val _networkState = MutableStateFlow(ConnectivityObserver.Status.Unavaliable)
    val networkState: StateFlow<ConnectivityObserver.Status> = _networkState.asStateFlow()

    private val _filterBottomSheetState = MutableStateFlow(FilterBottomState())
    val filterBottomSheetState = _filterBottomSheetState.asStateFlow()

    private val _pagingState = MutableStateFlow(ExplorePagingAdapterLoadState())
    val pagingState: StateFlow<ExplorePagingAdapterLoadState> = _pagingState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private var handler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.d(throwable.toString())
    }

    init {
        viewModelScope.launch(handler) {
            collectNetworkState()
            collectLanguageIsoCode()
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
                _language.value = language
                getGenreListByCategoriesState()
            }
        }
    }

    fun isNetworkAvaliable(): Boolean {
        return networkState.value.isAvaliable()
    }

    fun multiSearch(query: String): Flow<PagingData<SearchDto>> {
        return if (query.isNotEmpty()) {
            exploreUseCases.multiSearchUseCase(query = query, language = language.value)
        } else {
            flow { PagingData.empty<SearchDto>() }
        }
    }

    fun discoverMovie(): Flow<PagingData<Movie>> {
        return exploreUseCases.discoverMovieUseCase(
            language = language.value,
            filterBottomState = filterBottomSheetState.value
        ).cachedIn(viewModelScope)
    }

    fun discoverTv(): Flow<PagingData<TvSeries>> {
        return exploreUseCases.discoverTvUseCase(
            language = language.value,
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
            is ExploreFragmentEvent.NavigateToDetailBottomSheet -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavigateTo(event.directions))
                }
            }
            is ExploreFragmentEvent.NavigateToPersonDetail -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavigateTo(event.directions))
                }
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
                viewModelScope.launch { _eventFlow.emit(UiEvent.PopBackStack) }
            }
        }
    }


    fun onAdapterLoadStateEvent(event: ExploreAdapterLoadStateEvent) {
        when (event) {
            is ExploreAdapterLoadStateEvent.PagingError -> {
                _pagingState.update { it.copy(errorUiText = event.uiText) }
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ShowSnackbar(event.uiText))
                }
            }
            is ExploreAdapterLoadStateEvent.FilterAdapterLoading -> {
                _pagingState.update {
                    it.copy(
                        filterAdapterState = it.filterAdapterState.copy(
                            isLoading = true
                        )
                    )
                }
            }
            is ExploreAdapterLoadStateEvent.FilterAdapterNotLoading -> {
                _pagingState.update {
                    it.copy(
                        filterAdapterState = it.filterAdapterState.copy(
                            isLoading = false
                        )
                    )
                }
            }
            is ExploreAdapterLoadStateEvent.SearchAdapterLoading -> {
                _pagingState.update {
                    it.copy(
                        searchAdapterState = it.searchAdapterState.copy(
                            isLoading = true
                        )
                    )
                }
            }
            is ExploreAdapterLoadStateEvent.SearchAdapterNotLoading -> {
                _pagingState.update {
                    it.copy(
                        searchAdapterState = it.searchAdapterState.copy(
                            isLoading = false
                        )
                    )
                }
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
                    getTvGenreList()
                } else {
                    getMovieGenreList()
                }
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ShowSnackbar(UiText.StringResource(R.string.internet_error)))
                Timber.e("Didn't download genreList $e")
            }
        }
    }

    private fun getMovieGenreList() {
        viewModelScope.launch(handler) {
            exploreUseCases.movieGenreListUseCase(language.value).collectLatest { genreList ->
                _genreList.value = genreList
            }
        }
    }

    private fun getTvGenreList() {
        viewModelScope.launch(handler) {
            exploreUseCases.tvGenreListUseCase(language.value).collectLatest { genreList ->
                _genreList.value = genreList
            }
        }
    }
}



