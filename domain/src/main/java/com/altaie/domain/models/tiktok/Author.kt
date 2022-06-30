package com.altaie.domain.models.tiktok


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("unique_id")
    val username: String? = null,
    @SerializedName("nickname")
    val nickname: String? = null,
    @SerializedName("avatar_168x168")
    private val avatarAddress: Address? = null,
) {
    val profileImageUrl get() = avatarAddress?.url
}