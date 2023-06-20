package com.prmto.mova_movieapp.feature_upcoming.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.core.presentation.util.collectFlow
import com.prmto.mova_movieapp.core.util.handlePagingLoadState.HandlePagingStateUpComingPagingAdapter
import com.prmto.mova_movieapp.databinding.FragmentUpComingBinding
import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingRemindEntity
import com.prmto.mova_movieapp.feature_upcoming.presentation.adapter.UpComingMovieAdapter
import dagger.hilt.android.AndroidEntryPoint

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

        collectData()

        setInfoClickListener()
        setOnRemindMeClickListener()
    }

    private fun setInfoClickListener() {
        upComingMovieAdapter.setOnInfoClickListener { upComingMovie ->
            val action =
                UpComingFragmentDirections.actionUpComingFragmentToDetailBottomSheet(
                    upComingMovie.movie,
                    null
                )
            findNavController().navigate(action)
        }
    }

    private fun setOnRemindMeClickListener() {
        upComingMovieAdapter.setOnRemindMeClickListener { upComingMovie ->
            viewModel.onEvent(
                UpComingEvent.OnClickRemindMe(
                    upcomingRemindEntity = UpcomingRemindEntity(
                        upComingMovie.movie.id,
                        upComingMovie.movie.title,
                        upComingMovie.movie.releaseDate ?: ""
                    ),
                    isAddedToRemind = upComingMovie.isAddedToRemind
                )
            )
        }
    }

    private fun collectData() {
        collectFlow(viewModel.state) { state ->
            binding.progressBar.isVisible = state.isLoading
            binding.upComingRecyclerView.isVisible = !state.isLoading

            binding.errorTextView.isVisible = state.error.isNotEmpty()
            binding.errorTextView.text = state.error
            upComingMovieAdapter.submitData(state.upComingMovieState)
        }
    }

    private fun handlePagingLoadStates() {
        HandlePagingStateUpComingPagingAdapter(
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