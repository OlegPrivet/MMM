package com.app.mmm.core.di

import android.content.Context
import androidx.work.WorkManager
import com.app.mmm.dataStore
import com.app.mmm.database.repository.ProductRepository
import com.app.mmm.database.repository.ProductRepositoryRoom
import com.app.mmm.datastore.PrefsDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    @ViewModelScoped
    fun provideProductRepository(@ApplicationContext context: Context): ProductRepository =
        ProductRepositoryRoom(context)

    @Provides
    @ViewModelScoped
    fun providePrefsDataStore(@ApplicationContext context: Context) = PrefsDataStore(context.dataStore)

    @Provides
    @ViewModelScoped
    fun provideWorkManager(@ApplicationContext context: Context) = WorkManager.getInstance(context)
}
