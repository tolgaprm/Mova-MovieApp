package com.prmto.mova_movieapp.feature_home.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.remote.api.ImageApi
import com.prmto.mova_movieapp.core.data.remote.api.ImageSize
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.databinding.NowPlayingRowBinding

class NowPlayingRecyclerAdapter :
    PagingDataAdapter<Movie, NowPlayingRecyclerAdapter.MovieViewHolder>(DiffUtilCallBack<Movie>()) {

    private var onItemClickListener: (Movie) -> Unit = {}

    class MovieViewHolder(
        private val binding: NowPlayingRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, context: Context, onItemClickListener: (Movie) -> Unit = {}) {
            binding.movieTitle.text = movie.title

            val voteCount = HandleUtils.convertingVoteCountToString(movie.voteCount)

            binding.voteAverage.text = context.getString(
                R.string.voteAverage, movie.voteAverage.toString(), voteCount
            )

            binding.backdropImage.load(
                ImageApi.getImage(
                    imageUrl = movie.posterPath, imageSize = ImageSize.W500.path
                )
            )

            binding.genresText.text = movie.genresBySeparatedByComma


            binding.root.setOnClickListener {
                onItemClickListener.invoke(movie)
            }

        }


    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            val context = holder.itemView.context
            holder.bind(movie = movie, context = context, onItemClickListener = onItemClickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NowPlayingRowBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(
            binding = binding
        )
    }

    fun setOnClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }

}

class DiffUtilCallBack<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is Movie && newItem is Movie) {
            val old = oldItem as Movie
            val new = newItem as Movie
            new.id == old.id
        } else {
            val old = oldItem as TvSeries
            val new = newItem as TvSeries
            old.id == new.id
        }
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is Movie) {
            oldItem as Movie == newItem as Movie
        } else {
            oldItem as TvSeries == newItem as TvSeries
        }
    }
}