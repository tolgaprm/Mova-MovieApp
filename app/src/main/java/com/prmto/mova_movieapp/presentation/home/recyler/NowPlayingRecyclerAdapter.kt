package com.prmto.mova_movieapp.presentation.home.recyler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import com.prmto.mova_movieapp.data.remote.ImageApi
import com.prmto.mova_movieapp.data.remote.ImageSize
import com.prmto.mova_movieapp.databinding.NowPlayingRowBinding
import com.prmto.mova_movieapp.domain.models.Movie

class NowPlayingRecyclerAdapter :
    PagingDataAdapter<Movie, NowPlayingRecyclerAdapter.MovieViewHolder>(DiffUtilCallBack()) {


    class MovieViewHolder(
        val binding: NowPlayingRowBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.voteAverage.text = movie.voteAverage.toString()
            movie.posterPath
        }

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie = movie)

            holder.binding.backdropImage.load(
                ImageApi.getImage(
                    imageUrl = movie.posterPath,
                    imageSize = ImageSize.W300.path
                )
            ) {
                ImageLoader.Builder(holder.itemView.context)
                    .crossfade(true)
                    .build()
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NowPlayingRowBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }
}

class DiffUtilCallBack : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }


}