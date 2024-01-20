package com.prmto.core_domain.use_case.movie

import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.fake.repository.FakeGenreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TestGetMovieGenreListUseCase {

    private lateinit var getMovieGenreListUseCase: GetMovieGenreListUseCase
    private lateinit var genreRepository: FakeGenreRepository

    @Before
    fun setUp() {
        genreRepository = FakeGenreRepository()
        getMovieGenreListUseCase = GetMovieGenreListUseCase(genreRepository)
    }

    @Test
    fun `get movie genre list`() = runTest {
        val movieGenreList = getMovieGenreListUseCase("en")
        assertThat(movieGenreList.first()).isEqualTo(genreRepository.genreListEn)
    }

    @Test
    fun `get movie genre list with different language`() = runTest {
        val movieGenreList = getMovieGenreListUseCase("tr")
        assertThat(movieGenreList.first()).isEqualTo(genreRepository.genreListTr)
    }
}