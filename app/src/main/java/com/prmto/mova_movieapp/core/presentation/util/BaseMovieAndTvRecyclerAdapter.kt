package com.prmto.mova_movieapp.core.presentation.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.dto.Genre
import com.prmto.mova_movieapp.databinding.MovieRowBinding
import com.prmto.mova_movieapp.feature_home.domain.models.Movie
import com.prmto.mova_movieapp.feature_home.domain.models.TvSeries
import com.prmto.mova_movieapp.feature_home.presentation.home.recyler.DiffUtilCallBack

abstract class BaseMovieAndTvRecyclerAdapter<T : Any>(
) : PagingDataAdapter<
        T, BaseMovieAndTvRecyclerAdapter.MovieViewHolder>(DiffUtilCallBack<T>()) {

    var itemClickListener: (T) -> Unit = {}

    class MovieViewHolder(
        val binding: MovieRowBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindMovie(
            movie: Movie,
            genreList: List<Genre>,
            context: Context
        ) {
            binding.tvMovieTvName.text = movie.title

            val genre =
                HandleUtils.handleConvertingGenreListToOneGenreString(genreList, movie.genreIds)
            val voteCount = HandleUtils.convertingVoteCountToString(movie.voteCount)
            val releaseDate = HandleUtils.convertToYearFromDate(movie.releaseDate)

            binding.tvReleaseDateGenre.text =
                context.getString(R.string.release_date_genre, releaseDate, genre)

            binding.voteAverage.text = context.getString(
                R.string.voteAverage,
                movie.voteAverage.toString(), voteCount
            )

        }

        fun bindTvSeries(
            tv: TvSeries,
            genreList: List<Genre>,
            context: Context
        ) {
            binding.tvMovieTvName.text = tv.name

            val genre =
                HandleUtils.handleConvertingGenreListToOneGenreString(genreList, tv.genreIds)
            val releaseDate = HandleUtils.convertToYearFromDate(tv.firstAirDate)
            val voteCount = HandleUtils.convertingVoteCountToString(tv.voteCount)

            binding.tvReleaseDateGenre.text =
                context.getString(R.string.release_date_genre, releaseDate, genre)

            binding.voteAverage.text = context.getString(
                R.string.voteAverage,
                tv.voteAverage.toString(),
                voteCount
            )
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = getItem(position)

        if (item is Movie) {

            holder.bindMovie(movie = item, genreList = genreList, context = context)
        }

        if (item is TvSeries) {
            holder.bindTvSeries(tv = item, genreList = genreList, context = context)
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

    var genreList: List<Genre> = emptyList()

    abstract fun passMovieGenreList(genreList: List<Genre>)
}

