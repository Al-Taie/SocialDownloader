package com.altaie.data.models.tiktok


import com.google.gson.annotations.SerializedName

data class Music(
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("avatar_medium")
    val avatarMedium: Address? = null,
    @SerializedName("owner_nickname")
    val ownerNickname: String? = null,
    @SerializedName("play_url")
    val playAddress: Address? = null,
    @SerializedName("title")
    val title: String? = null
)