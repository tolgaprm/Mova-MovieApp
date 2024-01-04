package com.prmto.explore_ui.adapter.viewHolder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.prmto.core_ui.util.ImageSize
import com.prmto.core_ui.util.ImageUtil
import com.prmto.explore_domain.model.PersonSearch
import com.prmto.explore_ui.databinding.SearchPersonRowBinding
import com.prmto.core_ui.R as CoreUIR

class SearchPersonViewHolder(
    val binding: SearchPersonRowBinding,
    val context: Context
) : ViewHolder(binding.root) {


    fun bindPerson(
        personSearch: PersonSearch,
        onClickPersonItem: (PersonSearch) -> Unit = {}
    ) {
        binding.ivProfile.load(
            ImageUtil.getImage(
                imageUrl = personSearch.profilePath,
                imageSize = ImageSize.W500.path
            )
        ) {
            error(CoreUIR.drawable.ic_person_white)
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