package com.prmto.mova_movieapp.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.prmto.mova_movieapp.core.data.util.Constants.UPCOMING_REMIND_TABLE_NAME

val Migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("Create Table $UPCOMING_REMIND_TABLE_NAME (`movieId` INTEGER NOT NULL, `movieTitle` TEXT NOT NULL, `movieReleaseDate` TEXT NOT NULL, PRIMARY KEY(`movieId`))")
    }
}