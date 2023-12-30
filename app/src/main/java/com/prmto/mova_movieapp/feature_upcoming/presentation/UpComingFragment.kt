package com.prmto.mova_movieapp.feature_upcoming.presentation

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prmto.core_domain.util.asString
import com.prmto.database.entity.movie.UpcomingRemindEntity
import com.prmto.mova_movieapp.core.presentation.base.fragment.BaseFragment
import com.prmto.mova_movieapp.core.presentation.util.collectFlow
import com.prmto.mova_movieapp.core.presentation.util.handlePagingLoadState.HandlePagingStateUpComingPagingAdapter
import com.prmto.mova_movieapp.databinding.FragmentUpComingBinding
import com.prmto.mova_movieapp.feature_upcoming.presentation.adapter.UpComingMovieAdapter
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
                    upcomingRemindEntity = UpcomingRemindEntity(
                        upComingMovie.movie.id,
                        upComingMovie.movie.title,
                        upComingMovie.movie.fullReleaseDate ?: ""
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