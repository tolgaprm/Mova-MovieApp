package com.prmto.mova_movieapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.prmto.mova_movieapp.domain.repository.DataStoreOperations
import com.prmto.mova_movieapp.util.Constants
import com.prmto.mova_movieapp.util.Constants.LOCALE_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject


class DataOperationsImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreOperations {


    private object PreferencesKey {
        val localeKey = stringPreferencesKey(LOCALE_KEY)
    }


    override suspend fun updateCurrentLocale(locale: String) {
        dataStore.edit {
            it[PreferencesKey.localeKey] = locale
        }
    }

    override fun getLocale(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map {
                val locale = it[PreferencesKey.localeKey] ?: Constants.DEFAULT_REGION
                locale
            }
    }
}