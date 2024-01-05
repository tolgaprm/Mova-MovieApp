package com.prmto.core_ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.models.tv.TvSeries

class DiffUtilCallBack<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is Movie && newItem is Movie) {
            val old = oldItem as Movie
            val new = newItem as Movie
            new.id == old.id
        } else {
            val old = oldItem as TvSeries
            val new = newItem as TvSeries
            old.id == new.id
        }
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is Movie) {
            oldItem as Movie == newItem as Movie
        } else {
            oldItem as TvSeries == newItem as TvSeries
        }
    }
}