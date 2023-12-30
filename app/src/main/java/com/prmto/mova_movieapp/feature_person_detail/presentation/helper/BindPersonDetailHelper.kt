package com.prmto.mova_movieapp.feature_person_detail.presentation.helper

import android.content.Context
import android.widget.ImageView
import androidx.core.view.isVisible
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.ImageUtil
import com.prmto.mova_movieapp.core.presentation.util.toolBarTextVisibilityByScrollPositionOfNestedScrollView
import com.prmto.mova_movieapp.databinding.FragmentPersonDetailBinding
import com.prmto.mova_movieapp.feature_person_detail.domain.model.CastForPerson
import com.prmto.mova_movieapp.feature_person_detail.domain.model.CrewForPerson
import com.prmto.mova_movieapp.feature_person_detail.domain.model.PersonDetail
import com.prmto.mova_movieapp.feature_person_detail.presentation.adapter.PersonCastMovieAdapter
import com.prmto.mova_movieapp.feature_person_detail.presentation.adapter.PersonCrewMovieAdapter

class BindPersonDetailHelper(
    private val binding: FragmentPersonDetailBinding,
    private val personCrewAdapter: PersonCrewMovieAdapter,
    private val personCastAdapter: PersonCastMovieAdapter,
    context: Context
) {

    init {
        toolBarTextVisibilityByScrollPositionOfNestedScrollView(
            nestedScrollView = binding.nestedScrollView,
            position = 1000,
            toolBarTitle = binding.txtToolBarTitle,
            toolbar = binding.toolbar,
            context = context
        )
    }

    fun bindPersonDetail(personDetail: PersonDetail) {
        bindAttributes(personDetail)

        bindCrewForPerson(personDetail.combinedCredits?.crew)

        bindCastForPerson(personDetail.combinedCredits?.cast)
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
            ImageUtil.getImage(imageUrl = personDetail.profilePath)
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
}