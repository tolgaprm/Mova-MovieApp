package com.prmto.core_domain.fake.repository

import com.prmto.core_domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeDataStoreOperations : DataStoreOperations {
    private val languageIsoCode = MutableStateFlow("en")
    val languageIsoCodeFlow = languageIsoCode.asStateFlow()

    private val uiMode = MutableStateFlow<Int>(1)
    val uiModeFlow = uiMode.asStateFlow()

    override suspend fun updateCurrentLanguageIsoCode(languageTag: String) {
        languageIsoCode.value = languageTag
    }

    override fun getLanguageIsoCode(): Flow<String> {
        return languageIsoCodeFlow
    }

    override suspend fun updateUIMode(uiMode: Int) {
        this.uiMode.value = uiMode
    }

    override fun getUIMode(): Flow<Int?> {
        return uiModeFlow
    }
}