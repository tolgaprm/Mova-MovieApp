package com.prmto.mova_movieapp.feature_explore.presentation.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.ImageLoader
import com.prmto.mova_movieapp.core.data.dto.Genre
import com.prmto.mova_movieapp.feature_explore.data.dto.SearchDto
import com.prmto.mova_movieapp.feature_explore.data.dto.toMovieSearch
import com.prmto.mova_movieapp.feature_explore.data.dto.toPersonSearch
import com.prmto.mova_movieapp.feature_explore.data.dto.toTvSearch
import com.prmto.mova_movieapp.feature_explore.domain.util.MediaType
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.viewHolder.SearchMovieViewHolder
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.viewHolder.SearchPersonViewHolder
import com.prmto.mova_movieapp.feature_explore.presentation.adapter.viewHolder.SearchTvViewHolder
import com.prmto.mova_movieapp.feature_home.domain.models.Movie
import javax.inject.Inject

class SearchRecyclerAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) :
    PagingDataAdapter<SearchDto, ViewHolder>(diffCallback = diffCallback) {

    private var movieGenreList: List<Genre> = emptyList()

    private var onItemClickListener: (Movie) -> Unit = {}


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
                    movieViewHolder.bindMovie(movieSearch = movieSearch)
                }
                MediaType.TV_SERIES.value -> {
                    val tvSearch = searchDto.toTvSearch()!!
                    val tvViewHolder = holder as SearchTvViewHolder
                    tvViewHolder.bindSearchTv(searchTv = tvSearch)
                }
                MediaType.PERSON.value -> {
                    val personSearch = searchDto.toPersonSearch()!!
                    val searchMovieHolder = holder as SearchPersonViewHolder
                    searchMovieHolder.bindPerson(search = personSearch)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            SearchViewType.MOVIE.ordinal -> {
                SearchMovieViewHolder.from(parent, imageLoader)
            }
            SearchViewType.TV.ordinal -> {
                SearchTvViewHolder.from(parent, imageLoader)
            }
            SearchViewType.PERSON.ordinal -> {
                SearchPersonViewHolder.from(parent, imageLoader)
            }
            else -> {
                SearchTvViewHolder.from(parent, imageLoader)
            }
        }
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
    MOVIE,
    TV,
    PERSON
}