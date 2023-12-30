package com.prmto.mova_movieapp.feature_explore.presentation.adapter.viewHolder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.domain.models.movie.Movie
import com.prmto.mova_movieapp.core.presentation.util.ImageSize
import com.prmto.mova_movieapp.core.presentation.util.ImageUtil
import com.prmto.mova_movieapp.databinding.NowPlayingRowBinding

class SearchMovieViewHolder(
    private val binding: NowPlayingRowBinding,
    val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bindMovie(
        movie: Movie,
        onMovieSearchItemClick: (Movie) -> Unit = {}
    ) {
        binding.backdropImage.load(
            ImageUtil.getImage(
                imageUrl = movie.posterPath,
                imageSize = ImageSize.W500.path
            )
        )


        binding.voteAverage.text = context.getString(
            R.string.voteAverage,
            movie.voteAverage.toString(),
            movie.formattedVoteCount
        )

        binding.genresText.text = movie.genreByOne

        binding.movieTitle.textSize = 16f
        binding.movieTitle.text = movie.title
        binding.txtCategory.visibility = View.VISIBLE
        binding.txtCategory.text = context.getString(R.string.movie)

        binding.root.setOnClickListener {
            onMovieSearchItemClick(movie)
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