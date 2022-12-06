package com.prmto.mova_movieapp.repository

import androidx.paging.PagingData
import com.prmto.mova_movieapp.data.models.detail.movie.MovieDetailDto
import com.prmto.mova_movieapp.data.models.detail.tv.TvDetailDto
import com.prmto.mova_movieapp.domain.models.Movie
import com.prmto.mova_movieapp.domain.models.TvSeries
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import com.prmto.mova_movieapp.presentation.filter_bottom_sheet.state.FilterBottomState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRemoteRepository : RemoteRepository {

    val movieGenreListLanguageTr = com.prmto.mova_movieapp.data.models.GenreList(
        genres = listOf(
            com.prmto.mova_movieapp.data.models.Genre(id = 1, "Aksiyon"),
            com.prmto.mova_movieapp.data.models.Genre(id = 2, "Macera"),
            com.prmto.mova_movieapp.data.models.Genre(id = 3, "Animasyon"),
            com.prmto.mova_movieapp.data.models.Genre(id = 4, "Komedi"),
        )

    )

    val movieGenreListLanguageEn = com.prmto.mova_movieapp.data.models.GenreList(
        listOf(
            com.prmto.mova_movieapp.data.models.Genre(id = 1, "Action"),
            com.prmto.mova_movieapp.data.models.Genre(id = 2, "Adventure"),
            com.prmto.mova_movieapp.data.models.Genre(id = 3, "Animation"),
            com.prmto.mova_movieapp.data.models.Genre(id = 4, "Comedy"),
        )
    )

    val tvGenreListLanguageTr = com.prmto.mova_movieapp.data.models.GenreList(
        genres = listOf(
            com.prmto.mova_movieapp.data.models.Genre(id = 1, "Aksiyon & Macera"),
            com.prmto.mova_movieapp.data.models.Genre(id = 2, "Suç"),
            com.prmto.mova_movieapp.data.models.Genre(id = 3, "Belgesel"),
            com.prmto.mova_movieapp.data.models.Genre(id = 4, "Aile"),
        )

    )

    val tvGenreListLanguageEn = com.prmto.mova_movieapp.data.models.GenreList(
        listOf(
            com.prmto.mova_movieapp.data.models.Genre(id = 1, "Action & Adventure"),
            com.prmto.mova_movieapp.data.models.Genre(id = 2, "Crime"),
            com.prmto.mova_movieapp.data.models.Genre(id = 3, "Documentry"),
            com.prmto.mova_movieapp.data.models.Genre(id = 4, "Family"),
        )
    )


    override suspend fun getMovieGenreList(language: String): com.prmto.mova_movieapp.data.models.GenreList {
        return if (language.lowercase() == "tr") {
            movieGenreListLanguageTr
        } else {
            movieGenreListLanguageEn
        }
    }

    override suspend fun getTvGenreList(language: String): com.prmto.mova_movieapp.data.models.GenreList {
        return if (language.lowercase() == "tr") {
            tvGenreListLanguageTr
        } else {
            tvGenreListLanguageEn
        }
    }

    override fun getNowPlayingMovies(language: String, region: String): Flow<PagingData<Movie>> {
        return flow {
            PagingData.empty<Movie>()
        }
    }

    override fun getPopularMovies(language: String): Flow<PagingData<Movie>> {
        return flow {
            PagingData.empty<Movie>()
        }
    }

    override fun getTopRatedMovies(language: String): Flow<PagingData<Movie>> {
        return flow {
            PagingData.empty<Movie>()
        }
    }

    override fun getPopularTvs(language: String): Flow<PagingData<TvSeries>> {
        return flow {
            PagingData.empty<TvSeries>()
        }
    }

    override fun getTopRatedTvs(language: String): Flow<PagingData<TvSeries>> {
        return flow {
            PagingData.empty<TvSeries>()
        }
    }

    override fun discoverMovie(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<Movie>> {
        return flow {
            PagingData.empty<Movie>()
        }
    }

    override fun discoverTv(
        language: String,
        filterBottomState: FilterBottomState
    ): Flow<PagingData<TvSeries>> {
        return flow {
            PagingData.empty<TvSeries>()
        }

    }

    override suspend fun getMovieDetail(language: String, movieId: Int): MovieDetailDto {
        TODO("Not yet implemented")
    }

    override suspend fun getTvDetail(language: String, tvId: Int): TvDetailDto {
        TODO("Not yet implemented")
    }
}