package com.prmto.mova_movieapp.presentation.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prmto.mova_movieapp.databinding.MovieRowBinding
import com.prmto.mova_movieapp.presentation.home.recyler.DiffUtilCallBack

abstract class BaseMovieAndTvRecyclerAdapter<T : Any>(
) : PagingDataAdapter<
        T, BaseMovieAndTvRecyclerAdapter.MovieViewHolder>(DiffUtilCallBack<T>()) {

    class MovieViewHolder(
        val binding: MovieRowBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        onBindViewHold(binding = holder.binding, position = position)
    }


    abstract fun onBindViewHold(binding: MovieRowBinding, position: Int)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieRowBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(
            binding = binding
        )
    }

}

