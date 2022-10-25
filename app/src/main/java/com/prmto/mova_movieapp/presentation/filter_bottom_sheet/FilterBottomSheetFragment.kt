package com.prmto.mova_movieapp.presentation.filter_bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.data.models.enums.Categories
import com.prmto.mova_movieapp.databinding.FragmentBottomSheetBinding
import com.prmto.mova_movieapp.domain.models.Genre
import com.prmto.mova_movieapp.presentation.explore.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterBottomSheetFragment() : BottomSheetDialogFragment() {

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
        binding.categoriesChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val categories =
                if (group.checkedChipId == binding.movieChip.id) Categories.MOVIE else Categories.TV

            // Update categories selected state
            viewModel.setCategoriesState(categories)

            // GetGenreList by categories state and language
            viewModel.getGenreListByCategoriesState()


        }

    }


    /**
     *
     * Add Chips in parentChip
     * @param parentChip The main chip-group to which chips will be added
     */
    private fun inflateGenreChips(chips: List<Genre>, parentChip: ChipGroup) {
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.categoriesState.collectLatest { categories ->
                        val chipId = if (categories == Categories.MOVIE) {
                            binding.movieChip.id
                        } else binding.tvSeriesChip.id

                        binding.categoriesChipGroup.check(chipId)
                    }
                }

                launch {
                    viewModel.genreList.collectLatest { genre ->
                        binding.genreListGroup.removeAllViews()
                        inflateGenreChips(chips = genre, binding.genreListGroup)
                    }
                }

                launch {
                    viewModel.periodsList.collect {
                        inflateGenreChips(chips = it, binding.timeChipGroup)
                    }
                }
            }
        }
    }

}


