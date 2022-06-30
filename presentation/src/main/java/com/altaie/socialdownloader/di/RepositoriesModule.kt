package com.altaie.socialdownloader.di

import com.altaie.data.data_sources.remote.TikTokApiService
import com.altaie.domain.models.tiktok.TikTokPost
import com.altaie.data.repositories.TikTokRepositoryImpl
import com.altaie.domain.repositories.TikTokRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun provideTikTokRepository(apiService: TikTokApiService): TikTokRepository =
        TikTokRepositoryImpl(apiService)

}