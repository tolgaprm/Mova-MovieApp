package com.prmto.mova_movieapp.core.presentation.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.MovieRowBinding
import com.prmto.mova_movieapp.feature_home.presentation.home.adapter.DiffUtilCallBack

abstract class BaseMovieAndTvRecyclerAdapter<T : Any>(
) : PagingDataAdapter<
        T, BaseMovieAndTvRecyclerAdapter.MovieViewHolder>(DiffUtilCallBack<T>()) {

    var itemClickListener: (T) -> Unit = {}

    class MovieViewHolder(
        val binding: MovieRowBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindMovie(
            movie: Movie,
            context: Context
        ) {
            binding.tvMovieTvName.text = movie.title

            movie.releaseDate?.let { releaseDateYear ->
                binding.tvReleaseDateGenre.text =
                    context.getString(
                        R.string.release_date_genre,
                        releaseDateYear,
                        movie.genreByOne
                    )
            }

            binding.voteAverage.text = context.getString(
                R.string.voteAverage,
                movie.voteAverage.toString(), movie.formattedVoteCount
            )

        }

        fun bindTvSeries(
            tv: TvSeries,
            context: Context
        ) {
            binding.tvMovieTvName.text = tv.name

            tv.firstAirDate?.let {
                binding.tvReleaseDateGenre.text =
                    context.getString(R.string.release_date_genre, tv.firstAirDate, tv.genreByOne)
            }

            binding.voteAverage.text = context.getString(
                R.string.voteAverage,
                tv.voteAverage.toString(),
                tv.formattedVoteCount
            )
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = getItem(position)

        if (item is Movie) {
            holder.bindMovie(movie = item, context = context)
        }

        if (item is TvSeries) {
            holder.bindTvSeries(tv = item, context = context)
        }

        onBindViewHold(binding = holder.binding, position = position, context = context)
    }

    abstract fun onBindViewHold(binding: MovieRowBinding, position: Int, context: Context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieRowBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(
            binding = binding
        )
    }

    fun setOnItemClickListener(listener: (T) -> Unit) {
        itemClickListener = listener
    }
}

fun <T : Any> BaseMovieAndTvRecyclerAdapter<T>.isEmpty(): Boolean {
    return this.itemCount <= 0
}

