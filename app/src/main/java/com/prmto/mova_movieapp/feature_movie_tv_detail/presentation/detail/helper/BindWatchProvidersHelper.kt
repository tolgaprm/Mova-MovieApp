package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.helper

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageApi
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.watchProvider.WatchProviderItemInfo

class BindWatchProvidersHelper(private val context: Context) {
    fun bind(
        listOfWatchProviderItem: List<WatchProviderItemInfo>,
        linearLayout: LinearLayout,
    ) {
        removeWatchProvidersInLayout(linearLayout)
        if (listOfWatchProviderItem.isEmpty()) {
            val image = inflateImage(context, linearLayout)
            image.setBackgroundResource(R.drawable.no_watch_provider)
            linearLayout.addView(image)
            return
        }

        for (item in listOfWatchProviderItem) {
            val image = inflateImage(context, linearLayout)
            image.load(
                ImageApi.getImage(imageUrl = item.logoPath)
            )
            image.setOnClickListener {
                Toast.makeText(context, item.providerName, Toast.LENGTH_SHORT).show()
            }
            linearLayout.addView(image)
        }
    }

    private fun inflateImage(
        context: Context,
        linearLayout: LinearLayout,
    ): ImageView {
        return LayoutInflater.from(context)
            .inflate(
                R.layout.watch_provider_image,
                linearLayout,
                false
            ) as ImageView
    }

    private fun removeWatchProvidersInLayout(linearLayout: LinearLayout) {
        if (linearLayout.childCount >= 1) {
            linearLayout.removeAllViewsInLayout()
        }
    }
}