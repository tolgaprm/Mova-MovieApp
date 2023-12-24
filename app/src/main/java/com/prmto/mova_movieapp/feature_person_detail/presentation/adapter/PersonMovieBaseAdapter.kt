package com.prmto.mova_movieapp.feature_person_detail.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.remote.api.ImageApi
import com.prmto.mova_movieapp.databinding.ActorMovieRowBinding
import com.prmto.mova_movieapp.feature_explore.domain.util.MediaType
import com.prmto.mova_movieapp.feature_person_detail.domain.model.CastForPerson
import com.prmto.mova_movieapp.feature_person_detail.domain.model.CrewForPerson

abstract class PersonMovieBaseAdapter<T : Any> :
    ListAdapter<T, PersonMovieBaseAdapter.PersonMovieViewHolder>(PersonMovieDiffUtil()) {

    var clickListener: (type: T) -> Unit = {}

    class PersonMovieViewHolder(
        private val binding: ActorMovieRowBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindCrew(crew: CrewForPerson) {
            binding.title.text = context.getString(R.string.department)
            binding.txtDetail.text = context.getString(R.string.director)
            bindTxtCategory(mediaType = crew.mediaType)
            binding.ivPoster.load(
                ImageApi.getImage(
                    imageUrl = crew.posterPath
                )
            )
        }

        fun bindCast(cast: CastForPerson) {
            binding.title.text = context.getString(R.string.character)
            binding.txtDetail.text = cast.character
            bindTxtCategory(mediaType = cast.mediaType)
            binding.ivPoster.load(
                ImageApi.getImage(
                    imageUrl = cast.posterPath
                )
            )
        }

        private fun bindTxtCategory(mediaType: String) {
            when (mediaType) {
                MediaType.MOVIE.value -> {
                    binding.txtCategory.isVisible = true
                    binding.txtCategory.text = context.getString(R.string.movie)
                }
                MediaType.TV_SERIES.value -> {
                    binding.txtCategory.isVisible = true
                    binding.txtCategory.text = context.getString(R.string.tv)
                }
                else -> binding.txtCategory.isVisible = false
            }
        }

        companion object {
            fun from(parent: ViewGroup): PersonMovieViewHolder {
                val inflate = LayoutInflater.from(parent.context)
                val binding = ActorMovieRowBinding.inflate(inflate, parent, false)
                return PersonMovieViewHolder(
                    binding = binding,
                        context = parent.context
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonMovieViewHolder {
        return PersonMovieViewHolder.from(parent = parent)
    }

    fun setOnClickListener(listener: (type: T) -> Unit) {
        clickListener = listener
    }

    override fun onBindViewHolder(holder: PersonMovieViewHolder, position: Int) {}
}

class PersonMovieDiffUtil<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is CrewForPerson && newItem is CrewForPerson) {
            val old = oldItem as CrewForPerson
            val new = newItem as CrewForPerson
            new.id == old.id
        } else {
            val old = oldItem as CastForPerson
            val new = newItem as CastForPerson
            old.id == new.id
        }
    }

    override fun areContentsTheSame(
        oldItem: T,
        newItem: T
    ): Boolean {
        return if (oldItem is CrewForPerson) {
            oldItem as CrewForPerson == newItem as CrewForPerson
        } else {
            oldItem as CastForPerson == newItem as CastForPerson
        }
    }
}