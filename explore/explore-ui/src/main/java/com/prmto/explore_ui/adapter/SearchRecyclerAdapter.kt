package com.prmto.explore_ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.explore_domain.model.MultiSearch
import com.prmto.explore_ui.adapter.viewHolder.SearchMovieViewHolder
import com.prmto.explore_ui.adapter.viewHolder.SearchPersonViewHolder
import com.prmto.explore_ui.adapter.viewHolder.SearchTvViewHolder

class SearchRecyclerAdapter :
    PagingDataAdapter<MultiSearch, ViewHolder>(diffCallback = diffCallback) {

    private var onMovieSearchClickListener: (Movie) -> Unit = {}
    private var onTvSearchClickListener: (TvSeries) -> Unit = {}
    private var onPersonSearchClickListener: (com.prmto.explore_domain.model.PersonSearch) -> Unit =
        {}

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        item?.let { multiSearch ->
            return when (multiSearch) {
                is MultiSearch.MovieItem -> SearchViewType.MOVIE.ordinal
                is MultiSearch.PersonItem -> SearchViewType.PERSON.ordinal
                is MultiSearch.TvSeriesItem -> SearchViewType.TV.ordinal
            }
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val multiSearch = getItem(position)
        multiSearch?.let {
            when (multiSearch) {
                is MultiSearch.MovieItem -> {
                    val movieViewHolder = holder as SearchMovieViewHolder
                    movieViewHolder.bindMovie(
                        movie = multiSearch.movie,
                        onMovieSearchItemClick = onMovieSearchClickListener
                    )
                }

                is MultiSearch.PersonItem -> {
                    val searchMovieHolder = holder as SearchPersonViewHolder
                    searchMovieHolder.bindPerson(
                        personSearch = multiSearch.person,
                        onClickPersonItem = onPersonSearchClickListener
                    )
                }

                is MultiSearch.TvSeriesItem -> {
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


val diffCallback = object : DiffUtil.ItemCallback<MultiSearch>() {
    override fun areItemsTheSame(
        oldItem: MultiSearch,
        newItem: MultiSearch
    ): Boolean {
        return when (oldItem) {
            is MultiSearch.MovieItem -> {
                oldItem.movie.id == ((newItem as? MultiSearch.MovieItem)?.movie?.id
                    ?: false)
            }

            is MultiSearch.PersonItem -> {
                oldItem.person.id == ((newItem as? MultiSearch.PersonItem)?.person?.id
                    ?: false)
            }

            is MultiSearch.TvSeriesItem -> {
                oldItem.tvSeries.id == ((newItem as? MultiSearch.TvSeriesItem)?.tvSeries?.id
                    ?: false)
            }
        }
    }

    override fun areContentsTheSame(
        oldItem: MultiSearch,
        newItem: MultiSearch
    ): Boolean {
        return when (oldItem) {
            is MultiSearch.MovieItem -> {
                oldItem.movie == ((newItem as? MultiSearch.MovieItem)?.movie
                    ?: false)
            }

            is MultiSearch.PersonItem -> {
                oldItem.person == ((newItem as? MultiSearch.PersonItem)?.person
                    ?: false)
            }

            is MultiSearch.TvSeriesItem -> {
                oldItem.tvSeries == ((newItem as? MultiSearch.TvSeriesItem)?.tvSeries
                    ?: false)
            }
        }
    }
}

enum class SearchViewType {
    MOVIE, TV, PERSON
}