package com.prmto.mova_movieapp.presentation.filter_bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.data.models.enums.Category
import com.prmto.mova_movieapp.data.models.enums.Sort
import com.prmto.mova_movieapp.databinding.FragmentBottomSheetBinding
import com.prmto.mova_movieapp.domain.models.Period
import com.prmto.mova_movieapp.presentation.explore.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        // Listen to change of the CategoriesChipGroup and update categories state in ViewModel
        binding.categoriesChipGroup.setOnCheckedStateChangeListener { group, _ ->
            val category =
                if (group.checkedChipId == binding.movieChip.id) Category.MOVIE else Category.TV

            // Update category selected state
            viewModel.setCategoryState(category)

            // GetGenreList by categories state and language
            viewModel.getGenreListByCategoriesState(viewModel.language.value)


        }

        // Listener Selected GenreList and update checkedGenreIdsState in ViewModel
        binding.genreListGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            viewModel.setGenreList(checkedIds)
        }

        // Listen to change of the PeriodChipGroup and update checkedPeriod in ViewModel
        binding.periodChipGroup.setOnCheckedStateChangeListener { group, _ ->
            viewModel.setCheckedPeriods(group.checkedChipId)
        }

        // Listen to change of the sortChipGroup and update checkedSort in ViewModel
        binding.sortChipGroup.setOnCheckedStateChangeListener { group, _ ->
            val checkedSort =
                if (group.checkedChipId == binding.popularity.id) Sort.Popularity else Sort.LatestRelease

            viewModel.setCheckedSortState(checkedSort)
        }

        binding.btnReset.setOnClickListener {
            viewModel.resetFilterBottomState()
        }

        binding.btnApply.setOnClickListener {
            findNavController().popBackStack()
        }



    }


    /**
     *
     * Add Chips in parentChip
     * @param parentChip The main chip-group to which chips will be added
     */
    private fun inflateGenreChips(
        chips: List<com.prmto.mova_movieapp.data.models.Genre>,
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

    /**
     *
     * Add Chips in parentChip
     * @param parentChip The main chip-group to which chips will be added
     */
    private fun inflatePeriodChips(chips: List<Period>, parentChip: ChipGroup) {
        chips.forEach {
            val chip = LayoutInflater.from(requireContext()).inflate(
                R.layout.chip, parentChip, false
            ) as Chip

            chip.text = it.time
            chip.id = it.id
            parentChip.addView(chip)
        }
    }


    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.filterBottomSheetState.collectLatest { filterBottomSheet ->

                        updateCheckCategoryFilter(filterBottomSheet.categoryState)

                        updateCheckedGenreFilters(filterBottomSheet.checkedGenreIdsState)

                        updateCheckedSortFilter(filterBottomSheet.checkedSortState)

                        updateCheckedPeriodFilter(filterBottomSheet.checkedPeriodId)
                    }
                }

                launch {
                    viewModel.isDownloadGenreOptions.collectLatest {
                        if (it) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.error_didnt_download),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }


                launch {
                    viewModel.periodState.collect {
                        inflatePeriodChips(it, binding.periodChipGroup)
                    }
                }

                launch {
                    viewModel.genreList.collect { genre ->
                        binding.genreListGroup.removeAllViews()
                        inflateGenreChips(chips = genre, binding.genreListGroup)
                    }
                }

                launch {
                    viewModel.language.collectLatest {
                        viewModel.setLocale(it)
                    }
                }
            }
        }
    }

    private fun updateCheckedPeriodFilter(checkedPeriodId: Int) {
        binding.periodChipGroup.check(checkedPeriodId)
    }


    private fun updateCheckedSortFilter(checkedSortState: Sort) {
        val checkedSortId =
            if (checkedSortState == Sort.Popularity) binding.popularity.id else binding.latestRelease.id

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
        val chipId = if (categoryState == Category.MOVIE) {
            binding.movieChip.id
        } else binding.tvSeriesChip.id

        binding.categoriesChipGroup.check(chipId)
    }

}


