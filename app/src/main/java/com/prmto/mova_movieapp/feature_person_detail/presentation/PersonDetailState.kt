package com.prmto.mova_movieapp.feature_person_detail.presentation

import com.prmto.mova_movieapp.feature_person_detail.domain.model.PersonDetail

data class PersonDetailState(
    val isLoading: Boolean = false,
    val isActor: Boolean = false,
    val personDetail: PersonDetail? = null
)
