package com.prmto.mova_movieapp.feature_person_detail.presentation

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageApi
import com.prmto.mova_movieapp.core.presentation.util.UiEvent
import com.prmto.mova_movieapp.core.presentation.util.asString
import com.prmto.mova_movieapp.databinding.FragmentPersonDetailBinding
import com.prmto.mova_movieapp.feature_person_detail.domain.model.CastForPerson
import com.prmto.mova_movieapp.feature_person_detail.domain.model.CrewForPerson
import com.prmto.mova_movieapp.feature_person_detail.domain.model.PersonDetail
import com.prmto.mova_movieapp.feature_person_detail.presentation.adapter.PersonCastMovieAdapter
import com.prmto.mova_movieapp.feature_person_detail.presentation.adapter.PersonCrewMovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonDetailFragment : Fragment(R.layout.fragment_person_detail) {

    private var _binding: FragmentPersonDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var personCrewAdapter: PersonCrewMovieAdapter
    private lateinit var personCastAdapter: PersonCastMovieAdapter

    private val viewModel: PersonDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPersonDetailBinding.bind(view)

        binding.txtBio.movementMethod = ScrollingMovementMethod()

        collectData()

        setupAdapters()

        binding.btnNavigateUp.setOnClickListener {
            findNavController().popBackStack()
        }

        _binding = binding
    }

    private fun setupAdapters() {
        personCastAdapter = PersonCastMovieAdapter()
        personCrewAdapter = PersonCrewMovieAdapter()
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collectLatest { personDetailState ->
                        binding.progressBar.isVisible = personDetailState.isLoading
                        personDetailState.personDetail?.let { personDetail ->
                            bindAttributes(personDetail = personDetail)

                            bindCrewForPerson(personDetail.combinedCredits.crew)

                            bindCastForPerson(personDetail.combinedCredits.cast)
                        }
                    }
                }
                launch {
                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is UiEvent.ShowSnackbar -> {
                                Snackbar.make(
                                    requireView(),
                                    event.uiText.asString(requireContext()),
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                            is UiEvent.NavigateTo -> {
                                return@collectLatest
                            }
                        }
                    }
                }
            }
        }
    }

    private fun bindCastForPerson(castList: List<CastForPerson>) {
        if (castList.isEmpty()) {
            binding.txtActorsWork.isVisible = false
            binding.actorRecylerView.isVisible = false
        } else {
            binding.actorRecylerView.adapter = personCastAdapter
            personCastAdapter.submitList(castList)
        }
    }

    private fun bindCrewForPerson(crewList: List<CrewForPerson>) {
        if (crewList.isEmpty()) {
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