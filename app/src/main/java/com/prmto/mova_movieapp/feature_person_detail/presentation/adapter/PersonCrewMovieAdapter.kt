package com.prmto.mova_movieapp.feature_person_detail.presentation.adapter

import com.prmto.mova_movieapp.feature_person_detail.domain.model.CrewForPerson

class PersonCrewMovieAdapter : PersonMovieBaseAdapter<CrewForPerson>() {

    override fun onBindViewHolder(holder: PersonMovieViewHolder, position: Int) {
        val crew = getItem(position)
        holder.bindCrew(crew = crew)
        holder.itemView.setOnClickListener {
            super.clickListener(crew)
        }
    }
}