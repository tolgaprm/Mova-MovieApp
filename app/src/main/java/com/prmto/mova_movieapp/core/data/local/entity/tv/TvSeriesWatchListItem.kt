package com.prmto.mova_movieapp.core.data.local.entity.tv

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prmto.mova_movieapp.core.data.util.Constants.TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME
import com.prmto.mova_movieapp.core.domain.models.tv.TvSeries


@Entity(tableName = TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME)
data class TvSeriesWatchListItem(
    @PrimaryKey val tvSeriesId: Int,
    @Embedded val tvSeries: TvSeries,
)
