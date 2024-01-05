package com.prmto.navigation

import androidx.navigation.NavController

class Navigator {
    lateinit var navController: NavController

    // navigate on main thread or nav component crashes sometimes
    fun navigateToFlow(navigateFlow: NavigateFlow) = when (navigateFlow) {
        NavigateFlow.HomeFlow -> navController.navigate(MainNavGraphDirections.actionGlobalHomeFlow())
        is NavigateFlow.AuthFlow -> navController.navigate(MainNavGraphDirections.actionGlobalAuthFlow())
        is NavigateFlow.BottomSheetDetailFlow -> navController.navigate(
            MainNavGraphDirections.actionGlobalDetailBottomSheetFlow(
                navigateFlow.movie,
                navigateFlow.tvSeries
            )
        )

        is NavigateFlow.DetailFlow -> navController.navigate(
            MainNavGraphDirections.actionGlobalDetailFlow(
                navigateFlow.movieId,
                navigateFlow.tvSeriesId
            )
        )

        is NavigateFlow.PersonDetailFlow -> navController.navigate(
            MainNavGraphDirections.actionGlobalPersonDetailFlow(
                navigateFlow.personId
            )
        )
    }
}