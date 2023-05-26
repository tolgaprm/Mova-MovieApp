package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.helper

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageApi
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.watch_provider.WatchProviderItemDetail

class BindWatchProvidersHelper(
    private val context: Context,
    private val onWatchProviderClick: (String) -> Unit = {}
) {

    fun bind(
        listOfWatchProviderItem: List<WatchProviderItemDetail>?,
        watchProviderLinks: List<String>?,
        linearLayout: LinearLayout,
    ) {
        removeWatchProvidersInLayout(linearLayout)
        if (listOfWatchProviderItem.isNullOrEmpty()) {
            val image = inflateImage(context, linearLayout)
            image.setBackgroundResource(R.drawable.no_watch_provider)
            linearLayout.addView(image)
            return
        }

        listOfWatchProviderItem.take(5).forEachIndexed { index, item ->
            val image = inflateImage(context, linearLayout)
            image.load(
                ImageApi.getImage(imageUrl = item.logoPath)
            )

            image.setOnClickListener {
                Toast.makeText(context, item.providerName, Toast.LENGTH_SHORT).show()

                when (item.providerName) {
                    WatchProviders.NETFLIX.providerName -> {
                        WatchProviders.NETFLIX.aa(
                            watchProviderLinks,
                            onWatchProviderClick = { url ->
                                onWatchProviderClick(url)
                            })
                    }

                    WatchProviders.APPLE_TV.providerName -> {
                        WatchProviders.APPLE_TV.aa(
                            watchProviderLinks,
                            onWatchProviderClick = { url ->
                                onWatchProviderClick(url)
                            })
                    }

                    WatchProviders.GOOGLE_PLAY_MOVIES.providerName -> {
                        WatchProviders.GOOGLE_PLAY_MOVIES.aa(
                            watchProviderLinks,
                            onWatchProviderClick = { url ->
                                onWatchProviderClick(url)
                            })
                    }

                    WatchProviders.AMAZON_VIDEO.providerName -> {
                        WatchProviders.AMAZON_VIDEO.aa(
                            watchProviderLinks,
                            onWatchProviderClick = { url ->
                                onWatchProviderClick(url)
                            })
                    }

                    WatchProviders.HBO_MAX.providerName -> {
                        WatchProviders.HBO_MAX.aa(
                            watchProviderLinks,
                            onWatchProviderClick = { url ->
                                onWatchProviderClick(url)
                            })
                    }

                    WatchProviders.BLUTV.providerName -> {
                        WatchProviders.BLUTV.aa(watchProviderLinks, onWatchProviderClick = { url ->
                            onWatchProviderClick(url)
                        })
                    }

                    WatchProviders.DISNEY_PLUS.providerName -> {
                        WatchProviders.DISNEY_PLUS.aa(
                            watchProviderLinks,
                            onWatchProviderClick = { url ->
                                onWatchProviderClick(url)
                            })
                    }

                    WatchProviders.HULU.providerName -> {
                        WatchProviders.HULU.aa(watchProviderLinks, onWatchProviderClick = { url ->
                            onWatchProviderClick(url)
                        })
                    }

                    WatchProviders.HBO_MAX_AMAZON_CHANNEL.providerName -> {
                        WatchProviders.HBO_MAX_AMAZON_CHANNEL.aa(
                            watchProviderLinks,
                            onWatchProviderClick = { url ->
                                onWatchProviderClick(url)
                            })
                    }

                    WatchProviders.VUDU.providerName -> {
                        WatchProviders.VUDU.aa(watchProviderLinks, onWatchProviderClick = { url ->
                            onWatchProviderClick(url)
                        })
                    }
                }
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

enum class WatchProviders(val providerName: String, val value: String) {
    NETFLIX(providerName = "Netflix", value = "netflix"),
    APPLE_TV(providerName = "Apple TV", value = "apple"),
    GOOGLE_PLAY_MOVIES(providerName = "Google Play Movies", value = "play.google"),
    AMAZON_VIDEO(providerName = "Amazon Video", value = "watch.amazon.com"),
    HBO_MAX(providerName = "HBO Max", value = "play.hbomax.com"),
    BLUTV(providerName = "blutv", value = "www.blutv.com"),
    DISNEY_PLUS(providerName = "Disney Plus", value = "www.disneyplus.com"),
    HULU(providerName = "Hulu", "www.hulu.com"),
    HBO_MAX_AMAZON_CHANNEL(providerName = "HBO Max Amazon Channel", value = "watch.amazon.com"),
    VUDU(providerName = "Vudu", value = "www.vudu.com");


    fun aa(listOfUrl: List<String>?, onWatchProviderClick: (String) -> Unit) {
        listOfUrl?.filter { it.contains(this.value) }?.let {
            onWatchProviderClick(it.first())
        }
    }
}