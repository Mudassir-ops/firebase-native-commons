package com.example.firebase_core.di

import com.example.firebase_core.data.remote.CrashReporterImpl
import com.example.firebase_core.domain.repository.CrashReporter
import com.example.firebase_core.usecase.LogFirebaseCrashlyticsUseCase
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CrashReporterModule {

    @Provides
    fun provideFirebaseCrashlytics(): FirebaseCrashlytics =
        FirebaseCrashlytics.getInstance()

    @Provides
    fun provideCrashReporter(
        crashlytics: FirebaseCrashlytics
    ): CrashReporter = CrashReporterImpl(crashlytics)

    @Provides
    fun provideCrashUseCase(
        crashReporter: CrashReporter
    ): LogFirebaseCrashlyticsUseCase = LogFirebaseCrashlyticsUseCase(crashReporter = crashReporter)

}