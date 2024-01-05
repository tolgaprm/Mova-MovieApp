package com.prmto.database.entity.tv

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.database.util.Constants.FAVORITE_TV_SERIES_TABLE_NAME

@Entity(tableName = FAVORITE_TV_SERIES_TABLE_NAME)
data class FavoriteTvSeries(
    @PrimaryKey val tvSeriesId: Int,
    @Embedded val tvSeries: TvSeries,
)