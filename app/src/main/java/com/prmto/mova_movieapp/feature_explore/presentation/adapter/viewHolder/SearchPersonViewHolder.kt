package com.prmto.mova_movieapp.feature_explore.presentation.adapter.viewHolder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.ImageLoader
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageApi
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageSize
import com.prmto.mova_movieapp.databinding.SearchPersonRowBinding
import com.prmto.mova_movieapp.feature_explore.domain.model.PersonSearch

class SearchPersonViewHolder(
    val binding: SearchPersonRowBinding,
    val imageLoader: ImageLoader,
    val context: Context
) : ViewHolder(binding.root) {


    fun bindPerson(
        personSearch: PersonSearch,
        onClickPersonItem: (PersonSearch) -> Unit = {}
    ) {
        binding.ivProfile.load(
            ImageApi.getImage(
                imageUrl = personSearch.profilePath,
                imageSize = ImageSize.W500.path
            ),
            imageLoader = imageLoader
        ) {
            error(R.drawable.ic_baseline_person_24)
        }

        binding.txtCategory.visibility = View.VISIBLE

        binding.txPersonName.text = personSearch.name
        personSearch.knownForDepartment?.let {
            binding.tvKnownForDepartment.text = personSearch.knownForDepartment
        }

        binding.root.setOnClickListener {
            onClickPersonItem(personSearch)
        }
    }


    companion object {
        fun from(
            parent: ViewGroup,
            imageLoader: ImageLoader,
        ): SearchPersonViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SearchPersonRowBinding.inflate(layoutInflater, parent, false)
            return SearchPersonViewHolder(
                binding = binding,
                imageLoader = imageLoader,
                context = parent.context
            )
        }
    }
}