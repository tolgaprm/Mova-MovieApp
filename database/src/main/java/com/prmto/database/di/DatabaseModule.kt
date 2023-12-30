package com.prmto.database.di

import android.content.Context
import androidx.room.Room
import com.prmto.database.MovaDatabase
import com.prmto.database.migration.Migration_1_2
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