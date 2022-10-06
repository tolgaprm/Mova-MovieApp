package com.prmto.mova_movieapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {

    suspend fun updateCurrentLocale(
        locale: String
    )

    fun getLocale(): Flow<String>
}