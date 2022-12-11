package com.prmto.mova_movieapp.domain.use_case.get_movie_detail

import com.prmto.mova_movieapp.R
import com.prmto.mova_movieapp.data.models.detail.movie.toMovieDetail
import com.prmto.mova_movieapp.domain.models.detail.MovieDetail
import com.prmto.mova_movieapp.domain.repository.RemoteRepository
import com.prmto.mova_movieapp.presentation.util.UiText
import com.prmto.mova_movieapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
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
                val response =
                    remoteRepository.getMovieDetail(language = language, movieId = movieId)
                val movieDetail = response.toMovieDetail()
                emit(Resource.Success(movieDetail))

            } catch (e: IOException) {
                emit(Resource.Error(UiText.StringResource(R.string.internet_error)))
            } catch (e: HttpException) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_error)))
                Timber.e("Error", e)
            }
        }
    }
}