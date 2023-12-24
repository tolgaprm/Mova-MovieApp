package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.helper

import android.content.Context
import android.widget.ImageView
import coil.load
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.core.data.remote.api.ImageApi
import com.prmto.mova_movieapp.core.data.remote.dto.genre.Genre
import com.prmto.mova_movieapp.core.presentation.util.HandleUtils
import com.prmto.mova_movieapp.databinding.FragmentDetailBinding
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.watchProvider.WatchProviderItem

open class BindAttributesDetailFragment(
    val binding: FragmentDetailBinding,
    val context: Context,
) {

    private val watchProvidersHelper: BindWatchProvidersHelper by lazy {
        BindWatchProvidersHelper(
            context = context
        )
    }

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

    protected fun bindWatchProviders(watchProviderItem: WatchProviderItem?) {
        watchProviderItem?.let { provider ->

            val streamWatchProviders = provider.stream
            val buyWatchProviders = provider.buy
            val rentWatchProviders = provider.rent

            watchProvidersHelper.bind(
                listOfWatchProviderItem = streamWatchProviders,
                linearLayout = binding.imvStreamLayout,
            )
            watchProvidersHelper.bind(
                listOfWatchProviderItem = buyWatchProviders,
                linearLayout = binding.imvBuyLayout,
            )
            watchProvidersHelper.bind(
                listOfWatchProviderItem = rentWatchProviders,
                linearLayout = binding.imvRentLayout,
            )

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