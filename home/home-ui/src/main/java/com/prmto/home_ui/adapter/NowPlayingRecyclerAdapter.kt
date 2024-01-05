package com.prmto.home_ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_ui.adapter.DiffUtilCallBack
import com.prmto.core_ui.databinding.NowPlayingRowBinding
import com.prmto.core_ui.util.ImageSize
import com.prmto.core_ui.util.ImageUtil
import com.prmto.core_ui.R as CoreUiR

class NowPlayingRecyclerAdapter :
    PagingDataAdapter<Movie, NowPlayingRecyclerAdapter.MovieViewHolder>(DiffUtilCallBack()) {

    private var onItemClickListener: (Movie) -> Unit = {}

    class MovieViewHolder(
        private val binding: NowPlayingRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, context: Context, onItemClickListener: (Movie) -> Unit = {}) {
            binding.movieTitle.text = movie.title


            binding.voteAverage.text = context.getString(
                CoreUiR.string.voteAverage,
                movie.voteAverage.toString(),
                movie.formattedVoteCount
            )

            binding.backdropImage.load(
                ImageUtil.getImage(
                    imageUrl = movie.posterPath,
                    imageSize = ImageSize.W500.path
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