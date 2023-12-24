package com.prmto.mova_movieapp.feature_explore.presentation.filter_bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.remote.dto.genre.Genre
import com.prmto.mova_movieapp.core.domain.models.Category
import com.prmto.mova_movieapp.core.domain.models.Sort
import com.prmto.mova_movieapp.core.domain.models.isMovie
import com.prmto.mova_movieapp.core.domain.models.isPopularity
import com.prmto.mova_movieapp.core.presentation.util.collectFlow
import com.prmto.mova_movieapp.databinding.FragmentBottomSheetBinding
import com.prmto.mova_movieapp.feature_explore.presentation.event.ExploreBottomSheetEvent
import com.prmto.mova_movieapp.feature_explore.presentation.explore.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding

    lateinit var viewModel: ExploreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ExploreViewModel::class.java]

        observeData()

        setViewsListener()
    }

    private fun setViewsListener() {
        binding.categoriesChipGroup.setOnCheckedStateChangeListener { group, _ ->
            val category =
                if (group.checkedChipId == binding.movieChip.id) Category.MOVIE else Category.TV

            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.UpdateCategory(category))
        }

        binding.genreListGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.UpdateGenreList(checkedIds))
        }

        binding.sortChipGroup.setOnCheckedStateChangeListener { group, _ ->
            val checkedSort =
                if (group.checkedChipId == binding.popularity.id) Sort.Popularity else Sort.LatestRelease
            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.UpdateSort(checkedSort))
        }

        binding.btnReset.setOnClickListener {
            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.ResetFilterBottomState)
        }

        binding.btnApply.setOnClickListener {
            viewModel.onEventBottomSheet(ExploreBottomSheetEvent.Apply)
        }
    }

    private fun inflateGenreChips(
        chips: List<Genre>,
        parentChip: ChipGroup
    ) {
        chips.forEach {
            val chip = LayoutInflater.from(requireContext()).inflate(
                R.layout.chip, parentChip, false
            ) as Chip

            chip.text = it.name
            chip.id = it.id
            parentChip.addView(chip)
        }
    }

    private fun observeData() {
        collectFlow(viewModel.filterBottomSheetState) { filterBottomSheet ->
            updateCheckCategoryFilter(filterBottomSheet.categoryState)

            updateCheckedGenreFilters(filterBottomSheet.checkedGenreIdsState)

            updateCheckedSortFilter(filterBottomSheet.checkedSortState)
        }

        collectFlow(viewModel.genreList) { genre ->
            binding.genreListGroup.removeAllViews()
            inflateGenreChips(chips = genre, binding.genreListGroup)

        }
    }

    private fun updateCheckedSortFilter(checkedSortState: Sort) {
        val checkedSortId =
            if (checkedSortState.isPopularity()) binding.popularity.id else binding.latestRelease.id

        binding.sortChipGroup.check(checkedSortId)
    }

    private fun updateCheckedGenreFilters(checkedGenreIds: List<Int>) {

        if (checkedGenreIds.isEmpty()) {
            binding.genreListGroup.clearCheck()
            return
        }
        checkedGenreIds.forEach {
            binding.genreListGroup.check(it)
        }
    }

    private fun updateCheckCategoryFilter(categoryState: Category) {
        val chipId = if (categoryState.isMovie()) {
            binding.movieChip.id
        } else binding.tvSeriesChip.id

        binding.categoriesChipGroup.check(chipId)
    }
}


