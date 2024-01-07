package com.prmto.core_domain.use_case.firebase.movie

import com.prmto.core_domain.R
import com.prmto.core_domain.models.movie.Movie
import com.prmto.core_domain.repository.firebase.FirebaseCoreMovieRepository
import com.prmto.core_domain.repository.firebase.FirebaseCoreRepository
import com.prmto.core_domain.util.Resource
import com.prmto.core_domain.util.SimpleResource
import com.prmto.core_domain.util.UiText
import javax.inject.Inject

class AddMovieToWatchListInFirebaseUseCase @Inject constructor(
    private val firebaseCoreRepository: FirebaseCoreRepository,
    private val firebaseCoreMovieRepository: FirebaseCoreMovieRepository
) {

    suspend operator fun invoke(
        moviesInWatchList: List<Movie>
    ): SimpleResource {
        val currentUser = firebaseCoreRepository.getCurrentUser()
        val userUid = currentUser?.uid
            ?: return Resource.Error(UiText.StringResource(R.string.must_login_able_to_add_in_list))

        return firebaseCoreMovieRepository.addMovieToWatchList(
            userUid = userUid,
            moviesInWatchList = moviesInWatchList
        )
    }
}