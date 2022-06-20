package com.bikcodeh.newsapp.di

import com.bikcodeh.newsapp.data.repository.TopNewsRepositoryImpl
import com.bikcodeh.newsapp.domain.repository.TopNewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesTopNewsRepository(
        topNewsRepositoryImpl: TopNewsRepositoryImpl
    ): TopNewsRepository
}