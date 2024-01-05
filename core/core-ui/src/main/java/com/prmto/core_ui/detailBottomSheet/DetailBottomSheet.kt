package com.prmto.core_ui.detailBottomSheet

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.core_ui.databinding.FragmentDetailBottomSheetBinding
import com.prmto.core_ui.util.AlertDialogUtil
import com.prmto.core_ui.util.collectFlow
import com.prmto.core_ui.util.setAddFavoriteIconByFavoriteState
import com.prmto.core_ui.util.setWatchListIconByWatchState
import com.prmto.navigation.NavigateFlow
import com.prmto.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint
import com.prmto.core_ui.R as CoreUiR

@AndroidEntryPoint
class DetailBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailBottomSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailBottomSheetBinding.inflate(inflater, container, false)
        _binding = binding

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonClickListeners()

        collectData()
    }

    private fun collectData() {
        collectUiEvent()

        collectFlow(viewModel.state) { state ->
            state.movie?.let {
                bindMovie(movie = it)
            }
            state.tvSeries?.let {
                bindTvSeries(tvSeries = it)
            }
            binding.btnFavoriteList.setAddFavoriteIconByFavoriteState(isFavorite = state.doesAddFavorite)
            binding.btnWatchingList.setWatchListIconByWatchState(isAddedWatchList = state.doesAddWatchList)
        }
    }

    private fun collectUiEvent() {
        collectFlow(viewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                is DetailBottomUiEvent.NavigateTo -> {
                    (requireActivity() as ToFlowNavigatable).navigateToFlow(uiEvent.navigateFlow)
                }

                is DetailBottomUiEvent.PopBackStack -> {
                    findNavController().popBackStack()
                }

                is DetailBottomUiEvent.ShowSnackbar -> {
                    return@collectFlow
                }

                is DetailBottomUiEvent.ShowAlertDialog -> {
                    AlertDialogUtil.showAlertDialog(
                        context = requireContext(),
                        title = CoreUiR.string.sign_in,
                        message = CoreUiR.string.must_login_able_to_add_in_list,
                        positiveBtnMessage = CoreUiR.string.sign_in,
                        negativeBtnMessage = CoreUiR.string.cancel,
                        onClickPositiveButton = {
                            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigateFlow.HomeFlow)
                        }
                    )
                }
            }
        }
    }

    private fun bindMovie(movie: Movie) {
        binding.apply {
            tvName.text = movie.title
            tvReleaseDate.text = movie.releaseDate
            tvOverview.text = movie.overview
            if (movie.posterPath != null) {
                loadImage(posterPath = movie.posterPath)
            }
            tvBottomInfoText.text =
                requireContext().getString(CoreUiR.string.detail_bottom_sheet_movie_info)
        }
    }

    private fun bindTvSeries(tvSeries: TvSeries) {
        binding.apply {
            tvName.text = tvSeries.name
            tvOverview.text = tvSeries.overview
            tvReleaseDate.text = tvSeries.firstAirDate
            if (tvSeries.posterPath != null) {
                loadImage(posterPath = tvSeries.posterPath)
            }
            tvBottomInfoText.text =
                requireContext().getString(CoreUiR.string.detail_bottom_sheet_tv_info)

            tvOverview.movementMethod = ScrollingMovementMethod()
        }
    }

    private fun loadImage(posterPath: String?) {
        binding.ivPoster.load(
            com.prmto.core_ui.util.ImageUtil.getImage(
                imageSize = com.prmto.core_ui.util.ImageSize.W185.path,
                imageUrl = posterPath
            )
        )
    }

    private fun setupButtonClickListeners() {
        binding.apply {
            detailSection.setOnClickListener {
                viewModel.onEvent(DetailBottomSheetEvent.NavigateToDetailFragment)
            }
            ibClose.setOnClickListener {
                viewModel.onEvent(DetailBottomSheetEvent.Close)
            }
            btnFavoriteList.setOnClickListener {
                viewModel.onEvent(DetailBottomSheetEvent.ClickedAddFavoriteList)
            }
            btnWatchingList.setOnClickListener {
                viewModel.onEvent(DetailBottomSheetEvent.ClickedAddWatchList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}