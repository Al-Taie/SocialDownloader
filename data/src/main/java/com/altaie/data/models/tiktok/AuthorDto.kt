package com.altaie.data.models.tiktok


import com.google.gson.annotations.SerializedName

data class AuthorDto(
    @SerializedName("unique_id")
    val username: String? = null,
    @SerializedName("nickname")
    val nickname: String? = null,
    @SerializedName("avatar_168x168")
    private val avatarAddress: AddressDto? = null,
) {
    val profileImageUrl get() = avatarAddress?.url
}