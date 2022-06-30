package com.altaie.domain.repositories

import com.altaie.domain.models.Resources
import com.altaie.domain.models.tiktok.TikTokPost
import com.altaie.domain.repositories.base.BaseRepository
import kotlinx.coroutines.flow.Flow


interface TikTokRepository : BaseRepository {
    fun getPost(id: String): Flow<Resources<TikTokPost?>>
}
