package com.altaie.domain.repositories

import com.altaie.domain.models.Resources
import com.altaie.domain.models.tiktok.TikTokPost
import com.altaie.domain.repositories.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Url


interface TikTokRepository : BaseRepository {
    fun getPost(id: String): Flow<Resources<TikTokPost?>>

    suspend fun getRedirectUrl(url: String): String?
}
