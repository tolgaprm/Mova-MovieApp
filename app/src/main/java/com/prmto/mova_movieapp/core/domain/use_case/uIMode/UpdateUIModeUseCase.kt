package com.prmto.mova_movieapp.core.domain.use_case.uIMode

import com.prmto.mova_movieapp.core.domain.repository.DataStoreOperations
import javax.inject.Inject

class UpdateUIModeUseCase @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) {
    suspend operator fun invoke(uiMode: Int) {
        dataStoreOperations.updateUIMode(uiMode)
    }
}