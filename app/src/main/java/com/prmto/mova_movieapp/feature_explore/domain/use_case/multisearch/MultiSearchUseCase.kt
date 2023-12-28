package com.prmto.mova_movieapp.feature_explore.domain.use_case.multisearch

import androidx.paging.PagingData
import com.prmto.mova_movieapp.feature_explore.domain.model.MultiSearch
import com.prmto.mova_movieapp.feature_explore.domain.repository.ExploreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MultiSearchUseCase @Inject constructor(
    private val repository: ExploreRepository,
) {

    operator fun invoke(
        query: String,
        language: String
    ): Flow<PagingData<MultiSearch>> {
        return repository.multiSearch(query = query, language = language)
    }
}