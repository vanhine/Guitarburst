package com.mrwinston.guitarburst.di

import android.app.Application
import android.content.Context
import com.mrwinston.guitarburst.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun providePiecesDao(@ApplicationContext context: Context): PiecesDao {
        return PiecesDatabase.getDatabase(context).PiecesDao()
    }

    @Provides
    fun providePiecesRepository(application: Application) = PiecesRepository(application)

    @Provides
    fun provideFavoritesDao(@ApplicationContext context: Context): FavoritesDao {
        return FavoritesDatabase.getDatabase(context).FavoritesDao()
    }

    @Provides
    fun provideFavoritesRepository(application: Application) = FavoritesRepository(application)
}