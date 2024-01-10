package com.prmto.core_data.repository

import com.google.common.truth.Truth.assertThat
import com.prmto.core_data.remote.api.TMDBApi
import com.prmto.core_data.util.MOVIE_GENRE_RESPONSE_JSON
import com.prmto.core_data.util.TV_GENRE_RESPONSE_JSON
import com.prmto.core_domain.models.genre.Genre
import com.prmto.core_domain.repository.GenreRepository
import com.prmto.core_testing.dispatcher.TestDispatcher
import com.prmto.core_testing.util.MockWebServerUtil
import com.prmto.core_testing.util.enqueueMockResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GenreRepositoryImplTest {

    private lateinit var genreRepository: GenreRepository
    private lateinit var tmdbApi: TMDBApi
    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        tmdbApi = MockWebServerUtil.createApiService(mockWebServer)
        genreRepository = GenreRepositoryImpl(
            tmdbApi = tmdbApi,
            dispatcherProvider = TestDispatcher()
        )
    }

    @Test
    fun getMovieGenreList_returnErrorFromApi_returnEmptyList() = runTest {
        mockWebServer.enqueueMockResponse(
            MOVIE_GENRE_RESPONSE_JSON,
            MockResponse().setResponseCode(500)
        )
        val genre = genreRepository.getMovieGenreList("en")
        assertThat(genre.genres).isEmpty()
    }

    @Test
    fun getMovieGenreList_returnSuccessFromApi_firstGenreNameIsSame() = runTest {
        mockWebServer.enqueueMockResponse(MOVIE_GENRE_RESPONSE_JSON)
        val genre = genreRepository.getMovieGenreList("en")
        assertThat(genre.genres.first()).isEqualTo(
            Genre(
                id = 28,
                name = "Action"
            )
        )
    }

    @Test
    fun getTvGenreList_returnErrorFromApi_returnEmptyList() = runTest {
        mockWebServer.enqueueMockResponse(
            TV_GENRE_RESPONSE_JSON,
            MockResponse().setResponseCode(500)
        )
        val genre = genreRepository.getTvGenreList("en")
        assertThat(genre.genres).isEmpty()
    }

    @Test
    fun getTvGenreList_returnSuccessFromApi_firstGenreNameIsSame() = runTest {
        mockWebServer.enqueueMockResponse(TV_GENRE_RESPONSE_JSON)
        val genre = genreRepository.getTvGenreList("en")
        assertThat(genre.genres.first()).isEqualTo(
            Genre(
                id = 10759,
                name = "Action & Adventure"
            )
        )
    }
}