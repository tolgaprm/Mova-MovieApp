package com.prmto.mova_movieapp.core.presentation.adapter

import com.prmto.core_domain.models.movie.Movie


class MovieAdapter : BaseListAdapter<Movie>() {

    override fun onBindViewHolder(holder: BaseListViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(
            context = holder.itemView.context,
            posterPath = movie.posterPath,
            movieTvName = movie.title,
            voteAverage = movie.voteAverage.toString(),
            voteCountByString = movie.formattedVoteCount,
            releaseDate = movie.releaseDate,
            genreByOne = movie.genreByOne
        )

        holder.itemView.setOnClickListener {
            super.itemClickListener(movie)
        }
    }
}


