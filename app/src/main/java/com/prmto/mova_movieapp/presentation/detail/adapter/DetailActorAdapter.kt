package com.prmto.mova_movieapp.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.ImageLoader
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.data.remote.ImageApi
import com.prmto.mova_movieapp.databinding.ActorRowBinding
import com.prmto.mova_movieapp.domain.models.credit.Cast
import javax.inject.Inject

class DetailActorAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) :
    ListAdapter<Cast, DetailActorAdapter.DetailActorAdapterViewHolder>(castItemCallback) {

    class DetailActorAdapterViewHolder(
        private val binding: ActorRowBinding,
        private val imageLoader: ImageLoader
    ) : ViewHolder(binding.root) {

        fun bind(cast: Cast) {
            binding.imvProfilePhoto.load(
                ImageApi.getImage(
                    imageUrl = cast.profilePath
                ),
                imageLoader = imageLoader,
            ) {
                error(R.drawable.ic_baseline_person_24)
            }
            binding.txtActorName.text = cast.name
            binding.txtCharacterName.text = cast.character
        }

        companion object {
            fun from(
                parent: ViewGroup,
                imageLoader: ImageLoader
            ): DetailActorAdapterViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ActorRowBinding.inflate(inflater, parent, false)
                return DetailActorAdapterViewHolder(binding, imageLoader)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailActorAdapterViewHolder {
        return DetailActorAdapterViewHolder.from(parent, imageLoader)
    }

    override fun onBindViewHolder(holder: DetailActorAdapterViewHolder, position: Int) {
        val cast = getItem(position)
        holder.bind(cast)
    }
}


val castItemCallback = object : ItemCallback<Cast>() {
    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem == newItem
    }
}