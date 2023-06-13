package com.prmto.mova_movieapp.feature_upcoming.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.core.util.HandlePagingLoadStates
import com.prmto.mova_movieapp.databinding.FragmentUpComingBinding
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingMovie
import com.prmto.mova_movieapp.feature_upcoming.presentation.adapter.UpComingMovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpComingFragment : Fragment(R.layout.fragment_up_coming) {

    private var _binding: FragmentUpComingBinding? = null
    val binding get() = _binding!!

    private val viewModel: UpComingViewModel by viewModels()

    private val upComingMovieAdapter by lazy { UpComingMovieAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentUpComingBinding.bind(view)
        _binding = binding

        binding.upComingRecyclerView.adapter = upComingMovieAdapter
        binding.upComingRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        handlePagingLoadStates()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collectLatest { state ->
                        binding.progressBar.isVisible = state.isLoading
                        binding.upComingRecyclerView.isVisible = !state.isLoading

                        binding.errorTextView.isVisible = state.error.isNotEmpty()
                        binding.errorTextView.text = state.error
                        upComingMovieAdapter.submitData(state.upComingMovieState)
                    }
                }
            }
        }

        upComingMovieAdapter.setOnInfoClickListener { upComingMovie ->
            val action =
                UpComingFragmentDirections.actionUpComingFragmentToDetailBottomSheet(
                    upComingMovie.movie,
                    null
                )
            findNavController().navigate(action)
        }

        upComingMovieAdapter.setOnRemindMeClickListener { upComingMovie ->
            viewModel.onEvent(
                UpComingEvent.OnClickRemindMe(
                    movieId = upComingMovie.movie.id,
                    movieTitle = upComingMovie.movie.title,
                    isAddedToRemind = upComingMovie.isAddedToRemind
                )
            )
        }
    }

    private fun handlePagingLoadStates() {
        HandlePagingLoadStates<UpcomingMovie>(
            upComingPagingAdapter = upComingMovieAdapter,
            onLoading = {
                viewModel.onEvent(UpComingEvent.Loading)
            },
            onError = { error ->
                viewModel.onEvent(UpComingEvent.Error(error.asString(requireContext())))
            },
            onNotLoading = {
                viewModel.onEvent(UpComingEvent.NotLoading)
            }
        )
    }
}