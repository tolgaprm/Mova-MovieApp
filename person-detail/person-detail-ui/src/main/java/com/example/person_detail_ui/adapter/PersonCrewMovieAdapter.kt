package com.example.person_detail_ui.adapter

import com.prmto.person_detail_domain.model.CrewForPerson

class PersonCrewMovieAdapter : PersonMovieBaseAdapter<CrewForPerson>() {

    override fun onBindViewHolder(holder: PersonMovieViewHolder, position: Int) {
        val crew = getItem(position)
        holder.bindCrew(crew = crew)
        holder.itemView.setOnClickListener {
            super.clickListener(crew)
        }
    }
}