package com.example.person_detail_ui.adapter

import com.prmto.person_detail_domain.model.CastForPerson

class PersonCastMovieAdapter : PersonMovieBaseAdapter<CastForPerson>() {

    override fun onBindViewHolder(holder: PersonMovieViewHolder, position: Int) {
        val castForPerson = getItem(position)
        holder.bindCast(cast = castForPerson)
        holder.itemView.setOnClickListener {
            super.clickListener(castForPerson)
        }
    }
}
