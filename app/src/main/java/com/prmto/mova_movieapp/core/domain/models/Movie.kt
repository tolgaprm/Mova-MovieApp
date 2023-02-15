package com.prmto.mova_movieapp.core.domain.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prmto.mova_movieapp.core.util.Constants.FAVORITE_MOVIE_TABLE_NAME
import com.prmto.mova_movieapp.core.util.Constants.MOVIE_WATCH_LIST_ITEM_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @PrimaryKey val id: Int,
    val overview: String,
    val title: String,
    val originalTitle: String,
    val posterPath: String?,
    var releaseDate: String?,
    val genreIds: List<Int>,
    val voteCount: Int,
    val genresBySeparatedByComma: String = "",
    val voteAverage: Double,
    val genreByOne: String = "",
    val voteCountByString: String = "" // Format like 1000 k
) : Parcelable {
    fun toFavoriteMovie(): FavoriteMovie {
        return FavoriteMovie(
            movieId = this.id,
            movie = this
        )
    }

    fun toMovieWatchListItem(): MovieWatchListItem {
        return MovieWatchListItem(
            movieId = this.id,
            movie = this
        )
    }
}


@Entity(tableName = FAVORITE_MOVIE_TABLE_NAME)
data class FavoriteMovie(
    @PrimaryKey val movieId: Int,
    @Embedded val movie: Movie
)

@Entity(tableName = MOVIE_WATCH_LIST_ITEM_TABLE_NAME)
data class MovieWatchListItem(
    @PrimaryKey val movieId: Int,
    @Embedded val movie: Movie
)