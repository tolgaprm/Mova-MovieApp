package com.prmto.mova_movieapp.data

import androidx.appcompat.app.AppCompatDelegate
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.common.truth.Truth.assertThat
import com.prmto.mova_movieapp.data.repository.DataOperationsImpl
import com.prmto.mova_movieapp.domain.repository.DataStoreOperations
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@ExperimentalCoroutinesApi
class DataStoreTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("TestDataStore")
    lateinit var dataStore: DataStore<Preferences>

    lateinit var dataStoreOperations: DataStoreOperations

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

    @Test
    fun languageIsoCode_testUpdateTr() = runTest {
        val job = launch {
            val trLanguageIsoCode = "tr"
            dataStoreOperations.updateCurrentLanguageIsoCode(trLanguageIsoCode)
            assertThat(dataStoreOperations.getLanguageIsoCode()).isEqualTo(trLanguageIsoCode)
        }
        job.cancel()
    }

    @Test
    fun uiMode_testFetchInitialPreferences() = runTest {
        val job = launch {
            val defaultUiMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            assertThat(dataStoreOperations.getUIMode().first()).isEqualTo(defaultUiMode)
        }
        job.cancel()
    }

    @Test
    fun uiMode_testUpdateModeNightYes() = runTest {
        val job = launch {
            val modeNightYes = AppCompatDelegate.MODE_NIGHT_YES
            dataStoreOperations.updateUIMode(modeNightYes)
            assertThat(dataStoreOperations.getUIMode()).isEqualTo(modeNightYes)
        }
        job.cancel()
    }

}