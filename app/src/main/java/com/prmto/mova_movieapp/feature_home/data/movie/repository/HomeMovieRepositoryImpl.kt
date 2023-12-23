package com.prmto.mova_movieapp.feature_home.data.movie.repository

import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.data.paging.getPagingMovies
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.feature_home.data.movie.datasource.HomeMovieRemoteDataSource
import com.prmto.mova_movieapp.feature_home.domain.movie.HomeMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeMovieRepositoryImpl @Inject constructor(
    private val homeMovieRemoteDataSource: HomeMovieRemoteDataSource
) : HomeMovieRepository {
    override fun getNowPlayingMovies(language: String, region: String): Flow<PagingData<Movie>> {
        return getPagingMovies { page ->
            homeMovieRemoteDataSource.getNowPlayingMovies(
                language = language,
                page = page,
                region = region
            )
        }
    }

    override fun getPopularMovies(language: String, region: String): Flow<PagingData<Movie>> {
        return getPagingMovies { page ->
            homeMovieRemoteDataSource.getPopularMovies(
                language = language,
                page = page,
                region = region
            )
        }
    }

    override fun getTopRatedMovies(language: String, region: String): Flow<PagingData<Movie>> {
        return getPagingMovies { page ->
            homeMovieRemoteDataSource.getTopRatedMovies(
                language = language,
                page = page,
                region = region
            )
        }
    }
}