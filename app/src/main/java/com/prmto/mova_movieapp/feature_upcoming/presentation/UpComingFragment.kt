package com.prmto.mova_movieapp.feature_upcoming.presentation

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prmto.core_domain.util.asString
import com.prmto.core_ui.base.fragment.BaseFragment
import com.prmto.core_ui.util.collectFlow
import com.prmto.upcoming_ui.UpComingEvent
import com.prmto.upcoming_ui.adapter.HandlePagingStateUpComingPagingAdapter
import com.prmto.upcoming_ui.adapter.UpComingMovieAdapter
import com.prmto.upcoming_ui.databinding.FragmentUpComingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpComingFragment : BaseFragment<FragmentUpComingBinding, UpComingViewModel>(
    inflater = FragmentUpComingBinding::inflate
) {

    override val viewModel: UpComingViewModel by viewModels()
    private val upComingMovieAdapter by lazy { UpComingMovieAdapter() }

    override fun onInitialize() {
        binding.upComingRecyclerView.adapter = upComingMovieAdapter
        handlePagingLoadStates()
        collectUpComingPagingData()
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
                    upComingMovie
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
        }
    }

    private fun collectUpComingPagingData() {
        collectFlow(viewModel.getUpComingMovies()) { upComingMoviePaging ->
            upComingMovieAdapter.submitData(upComingMoviePaging)
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