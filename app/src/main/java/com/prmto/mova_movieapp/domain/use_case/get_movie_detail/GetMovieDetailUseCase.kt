package com.prmto.mova_movieapp.domain.use_case.get_movie_detail

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.data.models.detail.movie.toMovieDetail
import com.prmto.mova_movieapp.domain.models.MovieDetail
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import com.prmto.mova_movieapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {

    operator fun invoke(
        language: String,
        movieId: Int
    ): Flow<Resource<MovieDetail>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response =
                    remoteRepository.getMovieDetail(language = language, movieId = movieId)
                val movieDetail = response.toMovieDetail()
                emit(Resource.Success(movieDetail))

            } catch (e: HttpException) {
                emit(Resource.Error(errorRes = R.string.internet_error))
            } catch (e: Exception) {
                emit(Resource.Error(errorRes = R.string.error))
                Timber.e("Error", e)
            }
        }
    }
}