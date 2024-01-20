package com.prmto.core_domain.use_case.database.movie

import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.fake.models.movie.movie
import com.prmto.core_domain.fake.repository.local.FakeLocalDatabaseRepository
import com.prmto.core_domain.fake.repository.local.FakeMovieLocalRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetFavoriteMovieIdsUseCaseTest {
    private lateinit var getFavoriteMovieIdsUseCase: GetFavoriteMovieIdsUseCase
    private lateinit var movieLocalRepository: FakeMovieLocalRepository

    @Before
    fun setUp() {
        movieLocalRepository = FakeMovieLocalRepository()
        val localDatabaseRepository = FakeLocalDatabaseRepository(
            movieLocalRepository = movieLocalRepository
        )
        getFavoriteMovieIdsUseCase = GetFavoriteMovieIdsUseCase(
            localDatabaseRepository
        )
    }

    @Test
    fun `get favorite movie ids`() = runTest {
        movieLocalRepository.insertMovieToFavoriteList(movie = movie(id = 1, title = "title1"))
        movieLocalRepository.insertMovieToFavoriteList(movie = movie(id = 2, title = "title2"))
        movieLocalRepository.insertMovieToFavoriteList(movie = movie(id = 3, title = "title3"))

        val favoriteMovieIds = getFavoriteMovieIdsUseCase()

        assertThat(favoriteMovieIds.first()).isEqualTo(listOf(1, 2, 3))
    }
}