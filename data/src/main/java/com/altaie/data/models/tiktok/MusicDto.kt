package com.altaie.data.models.tiktok


import com.google.gson.annotations.SerializedName

data class MusicDto(
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("avatar_medium")
    val avatarMedium: AddressDto? = null,
    @SerializedName("owner_nickname")
    val ownerNickname: String? = null,
    @SerializedName("play_url")
    val playAddress: AddressDto? = null,
    @SerializedName("title")
    val title: String? = null
) {
    val url get() = playAddress?.url
}