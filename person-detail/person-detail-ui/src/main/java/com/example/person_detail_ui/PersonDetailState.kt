package com.example.person_detail_ui

import com.prmto.person_detail_domain.model.PersonDetail

data class PersonDetailState(
    val isLoading: Boolean = false,
    val personDetail: PersonDetail? = null
)
