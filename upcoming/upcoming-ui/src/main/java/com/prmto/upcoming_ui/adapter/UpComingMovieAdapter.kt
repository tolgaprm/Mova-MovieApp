package com.prmto.upcoming_ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.prmto.core_domain.util.DateFormatUtils
import com.prmto.upcoming_domain.model.UpcomingMovie
import com.prmto.upcoming_ui.R
import com.prmto.upcoming_ui.databinding.ComingSoonItemBinding

class UpComingMovieAdapter :
    PagingDataAdapter<UpcomingMovie, UpComingMovieAdapter.UpComingViewHolder>(
        UpComingDiffUtilCallback()
    ) {
    private var infoListener: (UpcomingMovie) -> Unit = {}
    private var remindMeListener: (UpcomingMovie) -> Unit = {}

    override fun onBindViewHolder(holder: UpComingViewHolder, position: Int) {
        val upComingMovie = getItem(position)

        upComingMovie?.let { upcomingMovie ->

            holder.bind(
                upcomingMovie = upcomingMovie
            )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpComingViewHolder {
        val binding = ComingSoonItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UpComingViewHolder(
            binding = binding
        )
    }

    fun setOnInfoClickListener(listener: (UpcomingMovie) -> Unit) {
        this.infoListener = listener
    }

    fun setOnRemindMeClickListener(listener: (UpcomingMovie) -> Unit) {
        this.remindMeListener = listener
    }

    inner class UpComingViewHolder(
        val binding: ComingSoonItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            upcomingMovie: UpcomingMovie
        ) {
            val movie = upcomingMovie.movie
            bindImages(posterPath = movie.posterPath)

            bindBody(
                movieTitle = movie.title,
                movieOverview = movie.overview,
                movieReleaseDate = movie.fullReleaseDate ?: "",
                movieGenres = movie.genresBySeparatedByComma
            )

            bindRemindMeIcons(isAddedToRemind = upcomingMovie.isAddedToRemind)

            setupClickListeners(upcomingMovie = upcomingMovie)
        }

        private fun bindImages(posterPath: String?) {
            binding.imvMoviePoster.load(
                com.prmto.core_ui.util.ImageUtil.getImage(
                    imageSize = com.prmto.core_ui.util.ImageSize.W300.path,
                    imageUrl = posterPath
                )
            )
            binding.imvMovieBackdrop.load(
                com.prmto.core_ui.util.ImageUtil.getImage(
                    imageSize = com.prmto.core_ui.util.ImageSize.W300.path,
                    imageUrl = posterPath
                )
            )
        }

        private fun bindBody(
            movieTitle: String,
            movieOverview: String,
            movieReleaseDate: String,
            movieGenres: String
        ) {

            binding.txtMovieName.text = movieTitle

            binding.txtOverview.text = movieOverview

            binding.txtReleaseDate.text =
                DateFormatUtils.convertDateFormat(movieReleaseDate)

            binding.txtGenre.text = movieGenres

        }

        private fun bindRemindMeIcons(
            isAddedToRemind: Boolean
        ) {
            val remindMeIconRes = if (isAddedToRemind) {
                R.drawable.ic_remind_me_added_white
            } else {
                R.drawable.ic_remind_me_white
            }

            binding.imvRemindMe.setImageResource(remindMeIconRes)
        }

        private fun setupClickListeners(upcomingMovie: UpcomingMovie) {
            binding.imvInfo.setOnClickListener {
                infoListener(upcomingMovie)
            }

            binding.imvRemindMe.setOnClickListener {
                remindMeListener(upcomingMovie)
            }
        }
    }
}

class UpComingDiffUtilCallback : DiffUtil.ItemCallback<UpcomingMovie>() {
    override fun areItemsTheSame(oldItem: UpcomingMovie, newItem: UpcomingMovie): Boolean {
        return oldItem.movie.id == newItem.movie.id
    }

    override fun areContentsTheSame(oldItem: UpcomingMovie, newItem: UpcomingMovie): Boolean {
        return oldItem == newItem
    }
}
