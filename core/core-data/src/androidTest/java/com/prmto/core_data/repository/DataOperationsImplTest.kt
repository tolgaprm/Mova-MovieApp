package com.prmto.core_data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.repository.DataStoreOperations
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Locale
import javax.inject.Inject

@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
class DataOperationsImplTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    private lateinit var dataStoreOperations: DataStoreOperations

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    @Before
    fun setup() {
        hiltAndroidRule.inject()
        dataStoreOperations = DataOperationsImpl(dataStore)
    }

    @Test
    fun languageIsoCode_testFetchInitialPreferences() = runTest {
        val job = launch {
            val defaultLanguageIsoCode = Locale.getDefault().toLanguageTag()
            assertThat(
                dataStoreOperations.getLanguageIsoCode().first()
            ).isEqualTo(defaultLanguageIsoCode)
        }
        job.cancel()
    }
}