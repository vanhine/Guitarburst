package com.mrwinston.guitarburst.di

import android.app.Application
import android.content.Context
import com.mrwinston.guitarburst.data.PiecesDao
import com.mrwinston.guitarburst.data.PiecesDatabase
import com.mrwinston.guitarburst.data.PiecesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    @Provides
    fun providePiecesDao(@ApplicationContext context: Context): PiecesDao {
        return PiecesDatabase.getDatabase(context).PiecesDao()
    }

    @Provides
    fun providePiecesRepository(application: Application) = PiecesRepository(application)
}