package com.prmto.mova_movieapp.feature_upcoming.domain.use_case

import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingRemindEntity
import com.prmto.mova_movieapp.feature_upcoming.domain.repository.UpComingLocalRepository
import javax.inject.Inject

class InsertUpComingRemindUseCase @Inject constructor(
    private val upComingLocalRepository: UpComingLocalRepository
) {
    suspend operator fun invoke(upcomingRemind: UpcomingRemindEntity) {
        upComingLocalRepository.insertUpcomingRemind(upcomingRemind)
    }
}