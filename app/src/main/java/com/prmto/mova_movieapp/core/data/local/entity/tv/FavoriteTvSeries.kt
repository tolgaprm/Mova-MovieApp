package com.prmto.mova_movieapp.core.data.local.entity.tv

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prmto.mova_movieapp.core.domain.models.TvSeries
import com.prmto.mova_movieapp.core.util.Constants.FAVORITE_TV_SERIES_TABLE_NAME


@Entity(tableName = FAVORITE_TV_SERIES_TABLE_NAME)
data class FavoriteTvSeries(
    @PrimaryKey val tvSeriesId: Int,
    @Embedded val tvSeries: TvSeries,
)