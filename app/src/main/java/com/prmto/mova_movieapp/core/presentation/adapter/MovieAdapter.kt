package com.prmto.mova_movieapp.core.presentation.adapter

import com.prmto.mova_movieapp.core.domain.models.Movie

class MovieAdapter : BaseListAdapter<Movie>() {

    override fun onBindViewHolder(holder: BaseListViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(
            context = holder.itemView.context,
            posterPath = movie.posterPath,
            movieTvName = movie.title,
            voteAverage = movie.voteAverage.toString(),
            voteCountByString = movie.voteCountByString,
            releaseDate = movie.releaseDate,
            genreByOne = movie.genreByOne
        )

        holder.itemView.setOnClickListener {
            super.itemClickListener(movie)
        }
    }
}


