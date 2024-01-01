package com.prmto.mova_movieapp.feature_explore.presentation.adapter.viewHolder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.presentation.util.ImageSize
import com.prmto.mova_movieapp.core.presentation.util.ImageUtil
import com.prmto.mova_movieapp.databinding.SearchPersonRowBinding

class SearchPersonViewHolder(
    val binding: SearchPersonRowBinding,
    val context: Context
) : ViewHolder(binding.root) {


    fun bindPerson(
        personSearch: com.prmto.explore_domain.model.PersonSearch,
        onClickPersonItem: (com.prmto.explore_domain.model.PersonSearch) -> Unit = {}
    ) {
        binding.ivProfile.load(
            ImageUtil.getImage(
                imageUrl = personSearch.profilePath,
                imageSize = ImageSize.W500.path
            )
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
            parent: ViewGroup
        ): SearchPersonViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SearchPersonRowBinding.inflate(layoutInflater, parent, false)
            return SearchPersonViewHolder(
                binding = binding,
                context = parent.context
            )
        }
    }
}