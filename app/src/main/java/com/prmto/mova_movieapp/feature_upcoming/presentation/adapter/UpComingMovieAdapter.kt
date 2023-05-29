package com.prmto.mova_movieapp.feature_upcoming.presentation.adapter

import com.prmto.mova_movieapp.core.domain.models.Movie

class UpComingMovieAdapter() : UpComingBaseAdapter<Movie>() {
    override fun onBindViewHolder(holder: UpComingViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(
                posterPath = movie.posterPath,
                movieTvName = movie.title,
                overview = movie.overview,
                releaseDate = movie.releaseDate ?: "",
                genresBySeparatedByComma = movie.genresBySeparatedByComma
            )
        }
    }
}
