package com.prmto.ui.detail.helper

import android.content.Context
import android.widget.ImageView
import coil.load
import com.prmto.domain.models.watchProvider.WatchProviderItem
import com.prmto.ui.R
import com.prmto.ui.databinding.FragmentDetailBinding
import com.prmto.core_ui.R as CoreUiR

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
            com.prmto.core_ui.util.ImageUtil.getImage(
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
            placeholder(CoreUiR.drawable.loading_animate)
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
        formattedVoteCount: String,
        ratingBarValue: Float,
        genresBySeparatedByComma: String,
    ) {
        binding.apply {
            ratingBar.rating = ratingBarValue
            txtGenres.text = genresBySeparatedByComma
            txtVoteAverageCount.text = context.getString(
                R.string.voteAverageDetail,
                voteAverage.toString().subSequence(0, 3),
                formattedVoteCount
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