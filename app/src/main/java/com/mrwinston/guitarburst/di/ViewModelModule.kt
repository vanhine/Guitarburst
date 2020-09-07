package com.mrwinston.guitarburst.di

import com.google.common.flogger.FluentLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object ViewModelModule {
    @Provides
    fun provideFlogger(): FluentLogger = FluentLogger.forEnclosingClass()
}