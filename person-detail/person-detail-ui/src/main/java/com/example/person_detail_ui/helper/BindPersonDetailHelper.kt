package com.example.person_detail_ui.helper

import android.content.Context
import android.widget.ImageView
import androidx.core.view.isVisible
import coil.load
import com.example.person_detail_ui.adapter.PersonCastMovieAdapter
import com.example.person_detail_ui.adapter.PersonCrewMovieAdapter
import com.example.person_detail_ui.databinding.FragmentPersonDetailBinding
import com.prmto.core_ui.util.ImageUtil
import com.prmto.person_detail_domain.model.CastForPerson
import com.prmto.person_detail_domain.model.CrewForPerson
import com.prmto.person_detail_domain.model.PersonDetail
import com.prmto.core_ui.R as CoreUiR

class BindPersonDetailHelper(
    private val binding: FragmentPersonDetailBinding,
    private val personCrewAdapter: PersonCrewMovieAdapter,
    private val personCastAdapter: PersonCastMovieAdapter,
    context: Context
) {

    init {
        com.prmto.core_ui.util.toolBarTextVisibilityByScrollPositionOfNestedScrollView(
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

            placeholder(CoreUiR.drawable.loading_animate)
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