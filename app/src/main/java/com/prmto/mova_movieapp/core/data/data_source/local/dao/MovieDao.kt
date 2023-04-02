package com.prmto.mova_movieapp.core.data.data_source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.prmto.mova_movieapp.core.data.data_source.local.models.movie.FavoriteMovie
import com.prmto.mova_movieapp.core.data.data_source.local.models.movie.MovieWatchListItem
import com.prmto.mova_movieapp.core.util.Constants.FAVORITE_MOVIE_TABLE_NAME
import com.prmto.mova_movieapp.core.util.Constants.MOVIE_WATCH_LIST_ITEM_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert
    suspend fun insertMovieToFavoriteList(favoriteMovie: FavoriteMovie)

    @Insert
    suspend fun insertMovieToWatchListItem(movieWatchListItem: MovieWatchListItem)

    @Delete
    suspend fun deleteMovieFromFavoriteList(favoriteMovie: FavoriteMovie)

    @Delete
    suspend fun deleteMovieFromWatchListItem(movieWatchListItem: MovieWatchListItem)


    @Query("SELECT movieId FROM $FAVORITE_MOVIE_TABLE_NAME")
    fun getFavoriteMovieIds(): Flow<List<Int>>

    @Query("SELECT movieId FROM $MOVIE_WATCH_LIST_ITEM_TABLE_NAME")
    fun getMovieWatchListItemIds(): Flow<List<Int>>

    @Query("SELECT * FROM $FAVORITE_MOVIE_TABLE_NAME")
    fun getFavoriteMovies(): Flow<List<FavoriteMovie>>

    @Query("SELECT * FROM $MOVIE_WATCH_LIST_ITEM_TABLE_NAME")
    fun getMovieWatchList(): Flow<List<MovieWatchListItem>>

}