package com.aruuu.app.di

import android.content.Context
import com.aruuu.app.data.local.ARUUUDatabase
import com.aruuu.app.data.local.LockedAppDao
import com.aruuu.app.data.local.IntruderDao
import com.aruuu.app.data.local.SecureCredentialManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ARUUUDatabase =
        ARUUUDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideLockedAppDao(database: ARUUUDatabase): LockedAppDao =
        database.lockedAppDao()

    @Singleton
    @Provides
    fun provideIntruderDao(database: ARUUUDatabase): IntruderDao =
        database.intruderDao()

    @Singleton
    @Provides
    fun provideSecureCredentialManager(
        @ApplicationContext context: Context
    ): SecureCredentialManager = SecureCredentialManager(context)
}
