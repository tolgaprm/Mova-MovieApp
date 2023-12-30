package com.prmto.database.entity.tv

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prmto.core_domain.models.tv.TvSeries
import com.prmto.database.util.Constants.TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME

@Entity(tableName = TV_SERIES_WATCH_LIST_ITEM_TABLE_NAME)
data class TvSeriesWatchListItem(
    @PrimaryKey val tvSeriesId: Int,
    @Embedded val tvSeries: TvSeries,
)
