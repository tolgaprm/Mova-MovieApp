package com.prmto.mova_movieapp.feature_movie_tv_detail.util


fun Int.isSelectedRecommendationTab(): Boolean {
    return this == Constants.RECOMMENDATION_TAB_INDEX
}

fun Int.isSelectedTrailerTab(): Boolean {
    return this == Constants.TRAILER_TAB_INDEX
}