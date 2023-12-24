package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.textview.MaterialTextView
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.FragmentDetailBinding
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.tv.CreatedBy
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.tv.TvDetail

class BindTvDetail(
    binding: FragmentDetailBinding,
    tvDetail: TvDetail,
    context: Context
) : BindAttributesDetailFragment(binding = binding, context = context) {

    init {
        bindImage(posterPath = tvDetail.posterPath)
        removeDirectorsInLayout()
        bindDetailInfoSection(
            voteAverage = tvDetail.voteAverage,
            voteCount = tvDetail.voteCount,
            ratingBarValue = tvDetail.ratingValue,
            genreList = tvDetail.genres
        )
        bindToolBarTitle(toolbarTitle = tvDetail.name)
        bindFilmName(filmName = tvDetail.name)
        bindReleaseDate(releaseDate = tvDetail.releaseDate)
        bindOverview(overview = tvDetail.overview)
        bindWatchProviders(watchProviderItem = tvDetail.watchProviders)
        bindCreatorNames(createdBy = tvDetail.createdBy)
        showSeasonText(season = tvDetail.numberOfSeasons)
        hideRuntimeTextAndClockIcon()
    }

    private fun bindCreatorNames(createdBy: List<CreatedBy>?) {
        if (createdBy.isNullOrEmpty()) {
            binding.creatorDirectorLinearLayout.removeAllViews()
            return
        }
        setCreatorNameByCountOfCreator(creatorCount = createdBy.count())
        createdBy.forEach { creator ->
            val creatorText = LayoutInflater.from(context)
                .inflate(
                    R.layout.creator_text,
                    binding.creatorDirectorLinearLayout,
                    false
                ) as MaterialTextView
            creatorText.text = creator.name
            creatorText.id = creator.id
            binding.creatorDirectorLinearLayout.addView(creatorText)
        }
    }

    private fun setCreatorNameByCountOfCreator(creatorCount: Int) {
        binding.txtDirectorOrCreatorName.text = if (creatorCount > 1) {
            context.getString(R.string.plural_creator_title)
        } else {
            context.getString(R.string.singular_creator_title)
        }
    }

    private fun showSeasonText(season: Int) {
        binding.apply {
            imvCircle.visibility = View.VISIBLE
            txtSeason.visibility = View.VISIBLE
            txtSeason.text = context.getString(
                R.string.season_count,
                season.toString()
            )
        }
    }

    private fun hideRuntimeTextAndClockIcon() {
        binding.txtRuntime.visibility = View.GONE
        binding.imvClockIcon.visibility = View.GONE
    }
}