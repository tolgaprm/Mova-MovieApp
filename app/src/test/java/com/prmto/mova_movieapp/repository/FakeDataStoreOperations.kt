package com.prmto.mova_movieapp.repository

import com.prmto.mova_movieapp.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDataStoreOperations : DataStoreOperations {

    private var locale: String = "tr"

    override suspend fun updateCurrentLocale(locale: String) {
        this.locale = locale
    }

    override fun getLocale(): Flow<String> {
        return flow {
            emit(locale)
        }
    }
}