package com.prmto.mova_movieapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {

    suspend fun updateCurrentLanguageIsoCode(
        languageTag: String
    )

    fun getLanguageIsoCode(): Flow<String>

    suspend fun updateUIMode(uiMode: Int)

    fun getUIMode(): Flow<Int>
}