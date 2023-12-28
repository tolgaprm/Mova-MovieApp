package com.prmto.mova_movieapp.feature_person_detail.presentation

import android.text.method.ScrollingMovementMethod
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prmto.mova_movieapp.core.presentation.base.fragment.BaseFragmentWithUiEvent
import com.prmto.mova_movieapp.core.presentation.util.collectFlow
import com.prmto.mova_movieapp.core.presentation.util.loadAd
import com.prmto.mova_movieapp.databinding.FragmentPersonDetailBinding
import com.prmto.mova_movieapp.feature_explore.domain.util.MediaType
import com.prmto.mova_movieapp.feature_person_detail.domain.model.CastForPerson
import com.prmto.mova_movieapp.feature_person_detail.domain.model.CrewForPerson
import com.prmto.mova_movieapp.feature_person_detail.domain.model.toMovie
import com.prmto.mova_movieapp.feature_person_detail.domain.model.toTvSeries
import com.prmto.mova_movieapp.feature_person_detail.presentation.adapter.PersonCastMovieAdapter
import com.prmto.mova_movieapp.feature_person_detail.presentation.adapter.PersonCrewMovieAdapter
import com.prmto.mova_movieapp.feature_person_detail.presentation.helper.BindPersonDetailHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailFragment :
    BaseFragmentWithUiEvent<FragmentPersonDetailBinding, PersonDetailViewModel>(
        inflater = FragmentPersonDetailBinding::inflate
    ) {
    override val viewModel: PersonDetailViewModel by viewModels()

    private val personCrewAdapter: PersonCrewMovieAdapter by lazy { PersonCrewMovieAdapter() }
    private val personCastAdapter: PersonCastMovieAdapter by lazy { PersonCastMovieAdapter() }
    private var bindingPersonDetailHelper: BindPersonDetailHelper? = null

    override fun onInitialize() {
        bindingPersonDetailHelper = BindPersonDetailHelper(
            binding = binding,
            personCrewAdapter = personCrewAdapter,
            personCastAdapter = personCastAdapter,
            context = requireContext()
        )
        binding.adView.loadAd()
        collectUiEvent()
        collectUiState()
        binding.txtBio.movementMethod = ScrollingMovementMethod()
        setClickListeners()
        addOnBackPressedCallback()
    }

    private fun collectUiState() {
        collectFlow(viewModel.state) { uiState ->
            binding.progressBar.isVisible = uiState.isLoading
            uiState.personDetail?.let { personDetail ->
                bindingPersonDetailHelper?.bindPersonDetail(personDetail = personDetail)
            }
        }
    }

    private fun collectUiEvent() {
        collectFlow(viewModel.consumableViewEvents) { event ->
            handleUiEvent(
                listOfUiEvent = event,
                onEventConsumed = viewModel::onEventConsumed
            )
        }
    }

    private fun setClickListeners() {
        binding.btnNavigateUp.setOnClickListener {
            findNavController().popBackStack()
        }
        setAdaptersClickListener()
    }

    private fun setAdaptersClickListener() {
        personCastAdapter.setOnClickListener { castForPerson ->
            val action = setupAction(castForPerson = castForPerson)
            findNavController().navigate(action)
        }

        personCrewAdapter.setOnClickListener { crewForPerson ->
            val action = setupAction(crewForPerson = crewForPerson)
            findNavController().navigate(action)
        }
    }

    private fun setupAction(crewForPerson: CrewForPerson): PersonDetailFragmentDirections.ActionPersonDetailFragmentToDetailBottomSheet {
        val action =
            PersonDetailFragmentDirections.actionPersonDetailFragmentToDetailBottomSheet(null, null)
        when (crewForPerson.mediaType) {
            MediaType.MOVIE.value -> {
                action.movie = crewForPerson.toMovie()
                action.tvSeries = null
            }

            MediaType.TV_SERIES.value -> {
                action.movie = null
                action.tvSeries = crewForPerson.toTvSeries()
            }
        }
        return action
    }

    private fun setupAction(castForPerson: CastForPerson): PersonDetailFragmentDirections.ActionPersonDetailFragmentToDetailBottomSheet {
        val action =
            PersonDetailFragmentDirections.actionPersonDetailFragmentToDetailBottomSheet(null, null)
        when (castForPerson.mediaType) {
            MediaType.MOVIE.value -> {
                action.movie = castForPerson.toMovie()
                action.tvSeries = null
            }

            MediaType.TV_SERIES.value -> {
                action.movie = null
                action.tvSeries = castForPerson.toTvSeries()
            }
        }
        return action
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingPersonDetailHelper = null
    }
}