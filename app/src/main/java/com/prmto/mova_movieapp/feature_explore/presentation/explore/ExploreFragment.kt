package com.prmto.mova_movieapp.feature_explore.presentation.explore

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.models.enums.Category
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.databinding.FragmentExploreBinding
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.SearchMoviesAdapter
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.SearchRecyclerAdapter
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.SearchTvSeriesAdapter
import com.prmto.mova_movieapp.feature_explore.presentation.event.ExploreFragmentEvent
import com.prmto.mova_movieapp.feature_explore.presentation.event.ExploreUiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment @Inject constructor(

) : Fragment(R.layout.fragment_explore) {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ExploreViewModel

    @Inject
    lateinit var searchRecyclerAdapter: SearchRecyclerAdapter

    @Inject
    lateinit var movieSearchAdapter: SearchMoviesAdapter

    @Inject
    lateinit var tvSearchAdapter: SearchTvSeriesAdapter

    private var movieDiscoverJob: Job? = null
    private var tvDiscoverJob: Job? = null
    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ExploreViewModel::class.java]
        val binding = FragmentExploreBinding.bind(view)
        _binding = binding
        binding.recyclerSearch.adapter = searchRecyclerAdapter
        binding.recylerDiscoverMovie.adapter = movieSearchAdapter
        binding.recylerDiscoverTv.adapter = tvSearchAdapter

        collectData()

        binding.filter.setOnClickListener {
            findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToFilterBottomSheetFragment())
        }

        binding.edtQuery.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    viewModel.onEventExploreFragment(ExploreFragmentEvent.MultiSearch(query = it.toString()))
                }
            }
        }
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.query.collectLatest { query ->
                        binding.edtQuery.setText(query)
                    }
                }

                launch {
                    viewModel.filterBottomSheetState.collectLatest { filterBottomState ->
                        when (filterBottomState.categoryState) {
                            Category.MOVIE -> {
                                viewModel.onEventExploreFragment(ExploreFragmentEvent.RemoveQuery)
                                movieDiscoverJob = launch {
                                    viewModel.discoverMovie().collectLatest {
                                        hideTvAndSearchAdapters()
                                        cancelTvAndSearchJobs()
                                        binding.recylerDiscoverMovie.visibility = View.VISIBLE
                                        movieSearchAdapter.submitData(it)
                                    }
                                }
                            }

                            Category.TV -> {
                                viewModel.onEventExploreFragment(ExploreFragmentEvent.RemoveQuery)
                                tvDiscoverJob = launch {
                                    viewModel.discoverTv().collectLatest {
                                        cancelMovieAndSearchJobs()
                                        hideMoviesAndSearchAdapter()
                                        binding.recylerDiscoverTv.visibility = View.VISIBLE
                                        tvSearchAdapter.submitData(it)
                                    }
                                }
                            }

                            Category.SEARCH -> {
                                searchJob = launch {
                                    viewModel.multiSearch(viewModel.query.value).collectLatest {
                                        cancelTvAndMovieJobs()
                                        hideTvAndMovieAdapters()
                                        binding.recyclerSearch.visibility = View.VISIBLE
                                        searchRecyclerAdapter.submitData(it)
                                    }
                                }
                            }
                        }
                    }
                }

                launch {
                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is ExploreUiEvent.ShowSnackbar -> {
                                Snackbar.make(
                                    requireView(),
                                    event.uiText.asString(requireContext()),
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                            is ExploreUiEvent.PopBackStack -> {
                                findNavController().popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun cancelMovieAndSearchJobs() {
        searchJob?.cancel()
        movieDiscoverJob?.cancel()
    }

    private fun cancelTvAndSearchJobs() {
        searchJob?.cancel()
        tvDiscoverJob?.cancel()
    }

    private fun cancelTvAndMovieJobs() {
        tvDiscoverJob?.cancel()
        movieDiscoverJob?.cancel()
    }

    private fun hideMoviesAndSearchAdapter() {
        binding.recylerDiscoverMovie.visibility = View.GONE
        binding.recyclerSearch.visibility = View.GONE
    }

    private fun hideTvAndSearchAdapters() {
        binding.recyclerSearch.visibility = View.GONE
        binding.recylerDiscoverTv.visibility = View.GONE
    }

    private fun hideTvAndMovieAdapters() {
        binding.recylerDiscoverTv.visibility = View.GONE
        binding.recylerDiscoverMovie.visibility = View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
