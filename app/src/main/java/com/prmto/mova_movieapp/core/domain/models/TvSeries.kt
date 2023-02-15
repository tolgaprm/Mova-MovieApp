package com.prmto.mova_movieapp.core.domain.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prmto.mova_movieapp.core.util.Constants.FAVORITE_TV_SERIES_TABLE_NAME
import com.prmto.mova_movieapp.core.util.Constants.TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class TvSeries(
    @PrimaryKey val id: Int,
    val overview: String,
    val name: String,
    val originalName: String,
    val posterPath: String?,
    val firstAirDate: String?,
    val genreIds: List<Int>,
    val voteCount: Int,
    val voteAverage: Double,
    val genreByOne: String = "",
    val voteCountByString: String = "" // Format like 1000 k
) : Parcelable {
    fun toFavoriteTvSeries(): FavoriteTvSeries {
        return FavoriteTvSeries(
            tvSeriesId = this.id,
            tvSeries = this
        )
    }

    fun toTvSeriesWatchListItem(): TvSeriesWatchListItem {
        return TvSeriesWatchListItem(
            tvSeriesId = this.id,
            tvSeries = this
        )
    }
}

@Entity(tableName = FAVORITE_TV_SERIES_TABLE_NAME)
data class FavoriteTvSeries(
    @PrimaryKey val tvSeriesId: Int,
    @Embedded val tvSeries: TvSeries
)

@Entity(tableName = TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME)
data class TvSeriesWatchListItem(
    @PrimaryKey val tvSeriesId: Int,
    @Embedded val tvSeries: TvSeries
)
