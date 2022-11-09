package com.prmto.mova_movieapp.repository

import androidx.appcompat.app.AppCompatDelegate
import com.prmto.mova_movieapp.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDataStoreOperations : DataStoreOperations {

    private var locale: String = "tr"
    private var uiMode: Int = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM


    override suspend fun updateCurrentLanguageIsoCode(languageTag: String) {
        this.locale = languageTag
    }

    override fun getLanguageIsoCode(): Flow<String> {
        return flow {
            emit(locale)
        }
    }

    override suspend fun updateUIMode(uiMode: Int) {
        this.uiMode = uiMode
    }

    override fun getUIMode(): Flow<Int> {
        return flow {
            emit(uiMode)
        }
    }
}