package com.altaie.data.data_sources.remote

import com.altaie.data.models.tiktok.TikTokPostDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TikTokApiService {
    @GET("detail/")
    suspend fun getPost(@Query("aweme_id") id: String): Response<TikTokPostDto>
}