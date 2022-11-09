package com.prmto.mova_movieapp.data.repository

import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.prmto.mova_movieapp.domain.repository.DataStoreOperations
import com.prmto.mova_movieapp.util.Constants.LOCALE_KEY
import com.prmto.mova_movieapp.util.Constants.UI_MODE_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import java.util.*
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
                val locale = it[PreferencesKey.localeKey] ?: Locale.getDefault().toLanguageTag()
                locale
            }
    }

    override suspend fun updateUIMode(uiMode: Int) {
        dataStore.edit {
            it[PreferencesKey.uiModeKey] = uiMode
        }
    }

    override fun getUIMode(): Flow<Int> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            val uiMode = it[PreferencesKey.uiModeKey] ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            uiMode
        }
    }
}

