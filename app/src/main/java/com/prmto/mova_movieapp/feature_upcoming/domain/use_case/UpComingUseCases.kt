package com.prmto.mova_movieapp.feature_upcoming.domain.use_case

data class UpComingUseCases(
    val deleteUpcomingRemindUseCase: DeleteUpcomingRemindUseCase,
    val insertUpComingRemindUseCase: InsertUpComingRemindUseCase,
    val getUpcomingMovieUseCase: GetUpcomingMovieUseCase
)
