package com.prmto.mova_movieapp.presentation.home.recyler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.data.remote.ImageApi
import com.prmto.mova_movieapp.data.remote.ImageSize
import com.prmto.mova_movieapp.databinding.NowPlayingRowBinding
import com.prmto.mova_movieapp.domain.models.Genre
import com.prmto.mova_movieapp.domain.models.Movie
import javax.inject.Inject

class NowPlayingRecyclerAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) :
    PagingDataAdapter<Movie, NowPlayingRecyclerAdapter.MovieViewHolder>(DiffUtilCallBack()) {

    private var movieGenreList: List<Genre> = emptyList()


    class MovieViewHolder(
        private val binding: NowPlayingRowBinding,
        val movieGenreList: List<Genre>,
        val imageLoader: ImageLoader
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, context: Context) {
            binding.movieTitle.text = movie.title

            binding.voteAverage.text = context.getString(
                R.string.voteAverage,
                movie.voteAverage.toString(), movie.voteCount.toString()
            )

            binding.backdropImage.load(
                ImageApi.getImage(
                    imageUrl = movie.posterPath,
                    imageSize = ImageSize.W500.path
                ),
                imageLoader = imageLoader
            )
            if (movie.genreIds.isNotEmpty()) {

                binding.genresText.text = handleGenreText(movie = movie)
            }

        }

        private fun handleGenreText(movie: Movie): String {
            var genreNames = ""
            movieGenreList.forEach { genre ->
                movie.genreIds.forEach {
                    if (it == genre.id) {
                        genreNames += "${genre.name}, "
                    }
                }
            }

            return genreNames.substring(startIndex = 0, endIndex = genreNames.length - 2)
        }

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            val context = holder.itemView.context
            holder.bind(movie = movie, context = context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NowPlayingRowBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(
            binding,
            movieGenreList = movieGenreList,
            imageLoader = imageLoader
        )
    }


    fun passMovieGenreList(movieGenreList: List<Genre>) {
        this.movieGenreList = movieGenreList
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