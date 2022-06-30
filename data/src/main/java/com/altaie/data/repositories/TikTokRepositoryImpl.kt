package com.altaie.data.repositories

import com.altaie.data.data_sources.remote.TikTokApiService
import com.altaie.domain.repositories.TikTokRepository
import javax.inject.Inject


class TikTokRepositoryImpl @Inject constructor(
    private val apiService: TikTokApiService
) : TikTokRepository {
    override fun getPost(id: String) = wrapWithFlow { apiService.getPost(id = id) }
}
