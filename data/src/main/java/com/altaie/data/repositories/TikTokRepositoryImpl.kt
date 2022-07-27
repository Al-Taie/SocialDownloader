package com.altaie.data.repositories

import com.altaie.data.data_sources.remote.TikTokApiService
import com.altaie.data.mappers.toModel
import com.altaie.domain.repositories.TikTokRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject


class TikTokRepositoryImpl @Inject constructor(
    private val apiService: TikTokApiService
) : TikTokRepository {
    override fun getPost(id: String) =
        wrapWithFlow({ apiService.getPost(id = id) }) { it.toModel() }

    override suspend fun getRedirectUrl(url: String) = withContext(Dispatchers.IO) {
        val okHttpClient = OkHttpClient
            .Builder()
            .followRedirects(followRedirects = false)
            .build()

        val request = Request
            .Builder()
            .url(url)
            .build()

        try {
            okHttpClient
                .newCall(request)
                .execute()
                .headers["Location"]
        } catch (e: Exception) {
            null
        }
    }
}
