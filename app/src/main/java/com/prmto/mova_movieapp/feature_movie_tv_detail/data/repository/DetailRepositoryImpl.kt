package com.prmto.mova_movieapp.feature_movie_tv_detail.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.mova_movieapp.core.domain.models.Movie
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.util.Constants.ITEMS_PER_PAGE_FOR_RECOMMENDATION
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper.toMovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper.toTvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.mapper.toVideo
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.paging_source.MovieRecPagingSource
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.paging_source.TvRecPagingSource
import com.prmto.mova_movieapp.feature_movie_tv_detail.data.remote.DetailApi
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.MovieDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.TvDetail
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.Videos
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailApi: DetailApi,
) : DetailRepository {

    override suspend fun getMovieDetail(language: String, movieId: Int): MovieDetail {
        return detailApi.getMovieDetail(
            language = language,
            movieId = movieId
        ).toMovieDetail()
    }

    override suspend fun getTvDetail(language: String, tvId: Int): TvDetail {
        return detailApi.getTvDetail(
            language = language,
            tvId = tvId
        ).toTvDetail()
    }

    override fun getRecommendationsForMovie(
        movieId: Int,
        language: String,
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE_FOR_RECOMMENDATION),
            pagingSourceFactory = {
                MovieRecPagingSource(
                    detailApi = detailApi,
                    language = language,
                    movieId = movieId
                )
            }
        ).flow
    }

    override fun getRecommendationsForTv(tvId: Int, language: String): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE_FOR_RECOMMENDATION),
            pagingSourceFactory = {
                TvRecPagingSource(
                    detailApi = detailApi,
                    language = language,
                    tvId = tvId
                )
            }
        ).flow
    }

    override suspend fun getMovieVideos(movieId: Int, language: String): Videos {
        return detailApi.getMovieVideos(
            movieId = movieId,
            language = language
        ).toVideo()
    }

    override suspend fun getTvVideos(tvId: Int, language: String): Videos {
        return detailApi.getTvVideos(
            tvId = tvId,
            language = language
        ).toVideo()
    }
}