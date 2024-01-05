package com.prmto.explore_domain.use_case.multisearch

import androidx.paging.PagingData
import com.prmto.explore_domain.model.MultiSearch
import com.prmto.explore_domain.repository.ExploreRepository
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