package com.prmto.mova_movieapp.feature_person_detail.presentation.adapter

import com.prmto.mova_movieapp.feature_person_detail.domain.model.CastForPerson
import com.prmto.mova_movieapp.feature_person_detail.util.PersonMovieBaseAdapter

class PersonCastMovieAdapter() : PersonMovieBaseAdapter<CastForPerson>() {

    override fun onBindViewHolder(holder: PersonMovieViewHolder, position: Int) {
        val castForPerson = getItem(position)
        holder.bindCast(cast = castForPerson)
        holder.itemView.setOnClickListener {
            super.clickListener(castForPerson)
        }
    }
}
