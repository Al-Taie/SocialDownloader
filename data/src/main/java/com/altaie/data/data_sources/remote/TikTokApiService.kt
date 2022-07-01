package com.altaie.data.data_sources.remote

import com.altaie.domain.models.tiktok.TikTokPost
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface TikTokApiService {
    @GET("detail/")
    suspend fun getPost(@Query("aweme_id") id: String): Response<TikTokPost>
}