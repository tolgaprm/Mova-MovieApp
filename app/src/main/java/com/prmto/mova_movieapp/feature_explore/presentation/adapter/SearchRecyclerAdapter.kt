package com.prmto.mova_movieapp.feature_explore.presentation.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.feature_explore.data.dto.SearchDto
import com.prmto.mova_movieapp.feature_explore.data.dto.toMovieSearch
import com.prmto.mova_movieapp.feature_explore.data.dto.toPersonSearch
import com.prmto.mova_movieapp.feature_explore.data.dto.toTvSearch
import com.prmto.mova_movieapp.feature_explore.domain.model.PersonSearch
import com.prmto.mova_movieapp.feature_explore.domain.model.toMovie
import com.prmto.mova_movieapp.feature_explore.domain.model.toTvSeries
import com.prmto.mova_movieapp.feature_explore.domain.util.MediaType
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.viewHolder.SearchMovieViewHolder
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.viewHolder.SearchPersonViewHolder
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.viewHolder.SearchTvViewHolder

class SearchRecyclerAdapter :
    PagingDataAdapter<SearchDto, ViewHolder>(diffCallback = diffCallback) {

    private var onMovieSearchClickListener: (Movie) -> Unit = {}
    private var onTvSearchClickListener: (TvSeries) -> Unit = {}
    private var onPersonSearchClickListener: (PersonSearch) -> Unit = {}

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        item?.let { searchDto ->
            return when (searchDto.mediaType) {
                MediaType.MOVIE.value -> SearchViewType.MOVIE.ordinal
                MediaType.TV_SERIES.value -> SearchViewType.TV.ordinal
                MediaType.PERSON.value -> SearchViewType.PERSON.ordinal
                else -> SearchViewType.MOVIE.ordinal
            }
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchDto = getItem(position)
        searchDto?.let {
            when (searchDto.mediaType) {
                MediaType.MOVIE.value -> {
                    val movieSearch = searchDto.toMovieSearch()!!
                    val movieViewHolder = holder as SearchMovieViewHolder
                    movieViewHolder.bindMovie(movieSearch = movieSearch, onMovieSearchItemClick = {
                        onMovieSearchClickListener(it.toMovie())
                    })
                }
                MediaType.TV_SERIES.value -> {
                    val tvSearch = searchDto.toTvSearch()!!
                    val tvViewHolder = holder as SearchTvViewHolder
                    tvViewHolder.bindSearchTv(searchTv = tvSearch, onSearchTvItemClick = {
                        onTvSearchClickListener(it.toTvSeries())
                    })
                }
                MediaType.PERSON.value -> {
                    val personSearch = searchDto.toPersonSearch()!!
                    val searchMovieHolder = holder as SearchPersonViewHolder
                    searchMovieHolder.bindPerson(personSearch = personSearch, onClickPersonItem = {
                        onPersonSearchClickListener(it)
                    })
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

    fun setOnPersonSearchClickListener(listener: (PersonSearch) -> Unit) {
        onPersonSearchClickListener = listener
    }
}


val diffCallback = object : DiffUtil.ItemCallback<SearchDto>() {
    override fun areItemsTheSame(oldItem: SearchDto, newItem: SearchDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchDto, newItem: SearchDto): Boolean {
        return oldItem == newItem
    }
}

enum class SearchViewType {
    MOVIE, TV, PERSON
}