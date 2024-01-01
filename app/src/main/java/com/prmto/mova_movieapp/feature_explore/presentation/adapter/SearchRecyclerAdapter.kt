package com.prmto.mova_movieapp.feature_explore.presentation.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.viewHolder.SearchMovieViewHolder
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.viewHolder.SearchPersonViewHolder
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.viewHolder.SearchTvViewHolder

class SearchRecyclerAdapter :
    PagingDataAdapter<com.prmto.explore_domain.model.MultiSearch, ViewHolder>(diffCallback = diffCallback) {

    private var onMovieSearchClickListener: (Movie) -> Unit = {}
    private var onTvSearchClickListener: (TvSeries) -> Unit = {}
    private var onPersonSearchClickListener: (com.prmto.explore_domain.model.PersonSearch) -> Unit =
        {}

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        item?.let { multiSearch ->
            return when (multiSearch) {
                is com.prmto.explore_domain.model.MultiSearch.MovieItem -> SearchViewType.MOVIE.ordinal
                is com.prmto.explore_domain.model.MultiSearch.PersonItem -> SearchViewType.PERSON.ordinal
                is com.prmto.explore_domain.model.MultiSearch.TvSeriesItem -> SearchViewType.TV.ordinal
            }
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val multiSearch = getItem(position)
        multiSearch?.let {
            when (multiSearch) {
                is com.prmto.explore_domain.model.MultiSearch.MovieItem -> {
                    val movieViewHolder = holder as SearchMovieViewHolder
                    movieViewHolder.bindMovie(
                        movie = multiSearch.movie,
                        onMovieSearchItemClick = onMovieSearchClickListener
                    )
                }

                is com.prmto.explore_domain.model.MultiSearch.PersonItem -> {
                    val searchMovieHolder = holder as SearchPersonViewHolder
                    searchMovieHolder.bindPerson(
                        personSearch = multiSearch.person,
                        onClickPersonItem = onPersonSearchClickListener
                    )
                }

                is com.prmto.explore_domain.model.MultiSearch.TvSeriesItem -> {
                    val tvViewHolder = holder as SearchTvViewHolder
                    tvViewHolder.bindSearchTv(
                        tvSeries = multiSearch.tvSeries,
                        onSearchTvItemClick = onTvSearchClickListener
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            SearchViewType.MOVIE.ordinal -> {
                SearchMovieViewHolder.from(parent)
            }

            SearchViewType.TV.ordinal -> {
                SearchTvViewHolder.from(parent)
            }

            SearchViewType.PERSON.ordinal -> {
                SearchPersonViewHolder.from(parent)
            }

            else -> {
                SearchTvViewHolder.from(parent)
            }
        }
    }

    fun setOnMovieSearchClickListener(listener: (Movie) -> Unit) {
        onMovieSearchClickListener = listener
    }

    fun setOnTvSearchClickListener(listener: (TvSeries) -> Unit) {
        onTvSearchClickListener = listener
    }

    fun setOnPersonSearchClickListener(listener: (com.prmto.explore_domain.model.PersonSearch) -> Unit) {
        onPersonSearchClickListener = listener
    }
}


val diffCallback = object : DiffUtil.ItemCallback<com.prmto.explore_domain.model.MultiSearch>() {
    override fun areItemsTheSame(
        oldItem: com.prmto.explore_domain.model.MultiSearch,
        newItem: com.prmto.explore_domain.model.MultiSearch
    ): Boolean {
        return when (oldItem) {
            is com.prmto.explore_domain.model.MultiSearch.MovieItem -> {
                oldItem.movie.id == ((newItem as? com.prmto.explore_domain.model.MultiSearch.MovieItem)?.movie?.id
                    ?: false)
            }

            is com.prmto.explore_domain.model.MultiSearch.PersonItem -> {
                oldItem.person.id == ((newItem as? com.prmto.explore_domain.model.MultiSearch.PersonItem)?.person?.id
                    ?: false)
            }

            is com.prmto.explore_domain.model.MultiSearch.TvSeriesItem -> {
                oldItem.tvSeries.id == ((newItem as? com.prmto.explore_domain.model.MultiSearch.TvSeriesItem)?.tvSeries?.id
                    ?: false)
            }
        }
    }

    override fun areContentsTheSame(
        oldItem: com.prmto.explore_domain.model.MultiSearch,
        newItem: com.prmto.explore_domain.model.MultiSearch
    ): Boolean {
        return when (oldItem) {
            is com.prmto.explore_domain.model.MultiSearch.MovieItem -> {
                oldItem.movie == ((newItem as? com.prmto.explore_domain.model.MultiSearch.MovieItem)?.movie
                    ?: false)
            }

            is com.prmto.explore_domain.model.MultiSearch.PersonItem -> {
                oldItem.person == ((newItem as? com.prmto.explore_domain.model.MultiSearch.PersonItem)?.person
                    ?: false)
            }

            is com.prmto.explore_domain.model.MultiSearch.TvSeriesItem -> {
                oldItem.tvSeries == ((newItem as? com.prmto.explore_domain.model.MultiSearch.TvSeriesItem)?.tvSeries
                    ?: false)
            }
        }
    }
}

enum class SearchViewType {
    MOVIE, TV, PERSON
}