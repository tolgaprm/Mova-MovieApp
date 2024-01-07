package com.prmto.core_data.remote.api

import com.google.common.truth.Truth.assertThat
import com.prmto.core_testing.util.MockWebServerUtil
import com.prmto.core_testing.util.enqueueMockResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class TMDBApiTest {

    private lateinit var tmdbApi: TMDBApi
    private val mockWebServer: MockWebServer = MockWebServer()

    @Before
    fun setUp() {
        tmdbApi = MockWebServerUtil.createApiService(mockWebServer)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun response_whenGetMovieGenreList_isNotNull() {
        runBlocking {
            mockWebServer.enqueueMockResponse("movie_genre_response.json")
            val responseBody = tmdbApi.getMovieGenreList("en")
            assertThat(responseBody).isNotNull()
        }
    }

    @Test
    fun requestPath_whenGetMovieGenreList_isSameWithRequest() {
        runBlocking {
            mockWebServer.enqueueMockResponse("movie_genre_response.json")
            tmdbApi.getTvGenreList("en")
            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/genre/tv/list?language=en")
        }
    }

    @Test
    fun response_whenGetTvGenreList_isNotNull() {
        runBlocking {
            mockWebServer.enqueueMockResponse("tv_genre_response.json")
            val responseBody = tmdbApi.getMovieGenreList("en")
            assertThat(responseBody).isNotNull()
        }
    }

    @Test
    fun requestPath_whenGetTvGenreList_isSameWithRequest() {
        runBlocking {
            mockWebServer.enqueueMockResponse("tv_genre_response.json")
            tmdbApi.getTvGenreList("en")
            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/genre/tv/list?language=en")
        }
    }
}