package com.prmto.core_data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.prmto.core_data.util.Constants.LOCALE_KEY
import com.prmto.core_data.util.Constants.UI_MODE_KEY
import com.prmto.core_domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

class DataOperationsImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreOperations {


    private object PreferencesKey {
        val localeKey = stringPreferencesKey(LOCALE_KEY)
        val uiModeKey = intPreferencesKey(UI_MODE_KEY)
    }


    override suspend fun updateCurrentLanguageIsoCode(languageTag: String) {
        dataStore.edit {
            it[PreferencesKey.localeKey] = languageTag
        }
    }

    override fun getLanguageIsoCode(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map {
                val languageTag =
                    it[PreferencesKey.localeKey] ?: Locale.getDefault().toLanguageTag()
                languageTag
            }
    }


    override suspend fun updateUIMode(uiMode: Int) {
        dataStore.edit {
            it[PreferencesKey.uiModeKey] = uiMode
        }
    }

    override fun getUIMode(): Flow<Int?> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[PreferencesKey.uiModeKey]
        }
    }
}

