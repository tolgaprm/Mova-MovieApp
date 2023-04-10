package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.helper

import android.content.Context
import android.widget.ImageView
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.data_source.remote.ImageApi
import com.prmto.mova_movieapp.core.data.dto.Genre
import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.core.util.getCountryIsoCode
import com.prmto.mova_movieapp.databinding.FragmentDetailBinding
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.dto.watch_provider.WatchProviderRegion

open class BindAttributesDetailFragment(
    val binding: FragmentDetailBinding,
    val context: Context,
) {

    protected fun bindImage(posterPath: String?) {
        binding.imvPoster.load(
            ImageApi.getImage(
                imageUrl = posterPath
            )
        ) {
            listener(
                onStart = {
                    binding.imvPoster.scaleType = ImageView.ScaleType.CENTER
                },
                onSuccess = { _, _ ->
                    binding.imvPoster.scaleType = ImageView.ScaleType.CENTER_CROP
                }
            )
            placeholder(R.drawable.loading_animate)

        }
    }

    protected fun bindFilmName(filmName: String) {
        binding.txtFilmName.text = filmName
    }

    protected fun bindOverview(overview: String?) {
        overview?.let {
            binding.txtOverview.text = overview
        }
    }

    protected fun bindWatchProviders(providerRegion: WatchProviderRegion?) {
        providerRegion?.let { provider ->

            var isHaveWatchProvider = false

            val watchProvider = when (context.getCountryIsoCode()) {
                "tr" -> provider.tr
                "us" -> provider.us
                "fr" -> provider.fr
                "de" -> provider.de
                "es" -> provider.es
                else -> provider.en
            }

            val streamLogoPath = watchProvider?.flatRate?.first()?.logoPath
            val buyLogoPath = watchProvider?.buy?.first()?.logoPath
            val rentLogoPath = watchProvider?.rent?.first()?.logoPath



            streamLogoPath?.let {
                isHaveWatchProvider = true
                binding.imvStream.load(
                    ImageApi.getImage(imageUrl = it)
                )
            } ?: binding.imvStream.setBackgroundResource(R.drawable.no_watch_provider)


            buyLogoPath?.let {
                isHaveWatchProvider = true
                binding.imvBuy.load(
                    ImageApi.getImage(imageUrl = it)
                )
            } ?: binding.imvBuy.setBackgroundResource(R.drawable.no_watch_provider)

            rentLogoPath?.let {
                isHaveWatchProvider = true
                binding.imvRent.load(
                    ImageApi.getImage(imageUrl = it)
                )
            } ?: binding.imvRent.setBackgroundResource(R.drawable.no_watch_provider)
        }
    }

    protected fun bindToolBarTitle(toolbarTitle: String) {
        binding.txtToolBarTitle.text = toolbarTitle
    }

    protected fun bindDetailInfoSection(
        voteAverage: Double,
        voteCount: Int,
        ratingBarValue: Float,
        genreList: List<Genre>,
    ) {
        val voteCountText = HandleUtils.convertingVoteCountToString(voteCount = voteCount)
        binding.apply {
            ratingBar.rating = ratingBarValue
            txtGenres.text =
                HandleUtils.convertGenreListToStringSeparatedByCommas(genreList = genreList)
            txtVoteAverageCount.text = context.getString(
                R.string.voteAverageDetail,
                voteAverage.toString().subSequence(0, 3),
                voteCountText
            )
        }
    }

    protected fun removeDirectorsInLayout() {
        if (binding.creatorDirectorLinearLayout.childCount > 1) {
            binding.creatorDirectorLinearLayout.removeViewsInLayout(
                1,
                binding.creatorDirectorLinearLayout.childCount - 1
            )
        }
    }

    protected fun bindReleaseDate(releaseDate: String) {
        binding.txtReleaseDate.text = releaseDate
    }

}