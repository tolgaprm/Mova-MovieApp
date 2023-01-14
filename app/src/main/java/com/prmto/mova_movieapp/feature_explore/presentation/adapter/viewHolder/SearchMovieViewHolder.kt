package com.prmto.mova_movieapp.feature_explore.presentation.adapter.viewHolder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageApi
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageSize
import com.prmto.mova_movieapp.databinding.NowPlayingRowBinding
import com.prmto.mova_movieapp.feature_explore.domain.model.MovieSearch

class SearchMovieViewHolder(
    private val binding: NowPlayingRowBinding,
    val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bindMovie(
        movieSearch: MovieSearch,
        onMovieSearchItemClick: (MovieSearch) -> Unit = {}
    ) {
        binding.backdropImage.load(
            ImageApi.getImage(
                imageUrl = movieSearch.posterPath,
                imageSize = ImageSize.W500.path
            )
        )


        binding.voteAverage.text = context.getString(
            R.string.voteAverage,
            movieSearch.voteAverage.toString(),
            movieSearch.voteCountByString
        )

        binding.genresText.text = movieSearch.genreByOneForMovie

        binding.movieTitle.textSize = 16f
        binding.movieTitle.text = movieSearch.title
        binding.txtCategory.visibility = View.VISIBLE
        binding.txtCategory.text = context.getString(R.string.movie)

        binding.root.setOnClickListener {
            onMovieSearchItemClick(movieSearch)
        }
    }

    companion object {

        fun from(
            parent: ViewGroup
        ): SearchMovieViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = NowPlayingRowBinding.inflate(layoutInflater, parent, false)
            return SearchMovieViewHolder(
                binding = binding,
                context = parent.context
            )
        }
    }
}