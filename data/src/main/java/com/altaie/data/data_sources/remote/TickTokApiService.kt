package com.altaie.data.data_sources.remote

import com.altaie.data.models.tiktok.TickTokPost
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TickTokApiService {
    @GET("detail")
    fun getPost(@Query("aweme_id") id: String): Response<TickTokPost>
}