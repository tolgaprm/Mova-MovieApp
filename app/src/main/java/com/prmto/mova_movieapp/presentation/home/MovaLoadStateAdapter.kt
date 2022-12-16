package com.prmto.mova_movieapp.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.LoadStateFooterItemBinding

class MovaLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MovaLoadStateAdapter.MovaLoadStateViewHolder>() {

    class MovaLoadStateViewHolder(
        private val binding: LoadStateFooterItemBinding,
        retry: () -> Unit
    ) : ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState, context: Context) {

            if (loadState is LoadState.Error) {
                binding.errorMsg.text = context.getString(R.string.oops_something_went_wrong)
            }

            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): MovaLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.load_state_footer_item, parent, false)
                val binding = LoadStateFooterItemBinding.bind(view)
                return MovaLoadStateViewHolder(binding, retry)
            }
        }
    }

    override fun onBindViewHolder(holder: MovaLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, holder.itemView.context)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovaLoadStateViewHolder {
        return MovaLoadStateViewHolder.create(parent, retry)
    }


}