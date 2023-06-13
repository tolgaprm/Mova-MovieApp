package com.prmto.mova_movieapp.feature_upcoming.domain.use_case

import com.prmto.mova_movieapp.feature_upcoming.domain.model.UpcomingRemindEntity
import com.prmto.mova_movieapp.feature_upcoming.domain.repository.UpComingLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingRemindMovieUseCase @Inject constructor(
    private val upComingLocalRepository: UpComingLocalRepository
) {
    operator fun invoke(): Flow<List<UpcomingRemindEntity>> {
        return upComingLocalRepository.getAllUpcomingRemind()
    }
}