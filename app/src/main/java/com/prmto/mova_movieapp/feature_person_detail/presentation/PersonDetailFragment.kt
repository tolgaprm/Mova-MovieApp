package com.prmto.mova_movieapp.feature_person_detail.presentation

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.gms.ads.AdRequest
import com.google.android.material.snackbar.Snackbar
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.remote.api.ImageApi
import com.prmto.mova_movieapp.core.presentation.util.BaseUiEvent
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.core.presentation.util.collectFlow
import com.prmto.mova_movieapp.core.util.toolBarTextVisibilityByScrollPositionOfNestedScrollView
import com.prmto.mova_movieapp.databinding.FragmentPersonDetailBinding
import com.prmto.mova_movieapp.feature_explore.domain.util.MediaType
import com.prmto.mova_movieapp.feature_person_detail.domain.model.CastForPerson
import com.prmto.mova_movieapp.feature_person_detail.domain.model.CrewForPerson
import com.prmto.mova_movieapp.feature_person_detail.domain.model.PersonDetail
import com.prmto.mova_movieapp.feature_person_detail.domain.model.toMovie
import com.prmto.mova_movieapp.feature_person_detail.domain.model.toTvSeries
import com.prmto.mova_movieapp.feature_person_detail.presentation.adapter.PersonCastMovieAdapter
import com.prmto.mova_movieapp.feature_person_detail.presentation.adapter.PersonCrewMovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailFragment : Fragment(R.layout.fragment_person_detail) {

    private var _binding: FragmentPersonDetailBinding? = null
    private val binding get() = _binding!!

    private val personCrewAdapter: PersonCrewMovieAdapter by lazy { PersonCrewMovieAdapter() }
    private val personCastAdapter: PersonCastMovieAdapter by lazy { PersonCastMovieAdapter() }

    private val viewModel: PersonDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPersonDetailBinding.bind(view)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.txtBio.movementMethod = ScrollingMovementMethod()

        collectData()

        setAdaptersClickListener()

        binding.btnNavigateUp.setOnClickListener {
            findNavController().popBackStack()
        }

        toolBarTextVisibilityByScrollPositionOfNestedScrollView(
            nestedScrollView = binding.nestedScrollView,
            position = 1000,
            toolBarTitle = binding.txtToolBarTitle,
            toolbar = binding.toolbar,
            context = requireContext()
        )

        addOnBackPressedCallback()

        _binding = binding
    }


    private fun addOnBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
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

    private fun collectData() {
        collectFlow(viewModel.state) { personDetailState ->
            binding.progressBar.isVisible = personDetailState.isLoading
            personDetailState.personDetail?.let { personDetail ->
                bindAttributes(personDetail = personDetail)

                bindCrewForPerson(personDetail.combinedCredits?.crew)

                bindCastForPerson(personDetail.combinedCredits?.cast)
            }
        }

        collectFlow(viewModel.eventFlow) { event ->
            when (event) {
                is BaseUiEvent.ShowSnackbar -> {
                    Snackbar.make(
                        requireView(),
                        event.uiText.asString(requireContext()),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is BaseUiEvent.NavigateTo -> {
                    return@collectFlow
                }

                else -> return@collectFlow
            }
        }
    }

    private fun bindCastForPerson(castList: List<CastForPerson>?) {
        if (castList.isNullOrEmpty()) {
            binding.txtActorsWork.isVisible = false
            binding.actorRecylerView.isVisible = false
        } else {
            binding.actorRecylerView.adapter = personCastAdapter
            personCastAdapter.submitList(castList)
        }
    }

    private fun bindCrewForPerson(crewList: List<CrewForPerson>?) {

        if (crewList.isNullOrEmpty()) {
            binding.txtAsDirectorWorks.isVisible = false
            binding.directorRecylerView.isVisible = false
        } else {
            binding.directorRecylerView.adapter = personCrewAdapter
            personCrewAdapter.submitList(crewList)
        }
    }

    private fun bindAttributes(personDetail: PersonDetail) {
        binding.imvPerson.load(
            ImageApi.getImage(imageUrl = personDetail.profilePath)
        ) {
            listener(
                onStart = {
                    binding.imvPerson.scaleType = ImageView.ScaleType.CENTER
                },
                onSuccess = { _, _ ->
                    binding.imvPerson.scaleType = ImageView.ScaleType.CENTER_CROP
                }
            )

            placeholder(R.drawable.loading_animate)
        }

        binding.txtPersonName.text = personDetail.name

        binding.txtToolBarTitle.text = personDetail.name

        personDetail.deathday?.let { deathday ->
            binding.txtDateOfDeath.isVisible = true
            binding.dateOfDeathTitle.isVisible = true
            binding.txtDateOfDeath.text = deathday
        }

        binding.txtDateOfBirth.text = personDetail.birthday

        if (personDetail.biography.isNotEmpty()) {
            binding.txtBio.text = personDetail.biography
        } else {
            binding.txtBio.isVisible = false
            binding.bioTitle.isVisible = false
        }

        binding.txtNation.text = personDetail.placeOfBirth
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}