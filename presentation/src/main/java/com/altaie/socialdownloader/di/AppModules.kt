package com.altaie.socialdownloader.di

import com.altaie.domain.repositories.TikTokRepository
import com.altaie.domain.usecases.GetIdFromUrlUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    @Singleton
    fun provideGetIdFromShortLinkUseCase(repository: TikTokRepository) = GetIdFromUrlUseCase(repository)

}