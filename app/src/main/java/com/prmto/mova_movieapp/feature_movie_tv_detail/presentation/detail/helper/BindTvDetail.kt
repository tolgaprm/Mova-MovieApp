package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.helper

import android.content.Context
import android.view.View
import com.prmto.core_ui.util.makeVisible
import com.prmto.domain.models.detail.tv.CreatedBy
import com.prmto.domain.models.detail.tv.TvDetail
import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.databinding.FragmentDetailBinding
import com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.helper.inflater.CreatorTextInflater

class BindTvDetail(
    binding: FragmentDetailBinding,
    tvDetail: TvDetail,
    context: Context
) : BindAttributesDetailFragment(binding = binding, context = context) {

    private val creatorTextInflater by lazy {
        CreatorTextInflater(
            context = context,
            viewGroup = binding.creatorDirectorLinearLayout
        )
    }

    init {
        bindImage(posterPath = tvDetail.posterPath)
        removeDirectorsInLayout()
        bindDetailInfoSection(
            voteAverage = tvDetail.voteAverage,
            formattedVoteCount = tvDetail.formattedVoteCount,
            ratingBarValue = tvDetail.ratingValue,
            genresBySeparatedByComma = tvDetail.genresBySeparatedByComma
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
        creatorTextInflater.createCreatorTexts(
            createdByList = createdBy
        )
        setCreatorNameByCountOfCreator(creatorCount = createdBy?.count() ?: 0)
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
            imvCircle.makeVisible()
            txtSeason.makeVisible()
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