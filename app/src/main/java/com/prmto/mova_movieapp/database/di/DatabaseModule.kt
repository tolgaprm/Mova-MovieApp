package com.prmto.mova_movieapp.database.di

import android.content.Context
import androidx.room.Room
import com.prmto.mova_movieapp.core.domain.use_case.*
import com.prmto.mova_movieapp.core.domain.use_case.database.*
import com.prmto.mova_movieapp.core.domain.use_case.database.tv.*
import com.prmto.mova_movieapp.database.MovaDatabase
import com.prmto.mova_movieapp.database.migration.Migration_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovaDatabase(
        @ApplicationContext context: Context,
    ): MovaDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MovaDatabase::class.java,
            "MovaDatabase"
        ).fallbackToDestructiveMigration()
            .addMigrations(
                Migration_1_2
            ).build()
    }
}