package com.altaie.data.models.tiktok


import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("cover")
    val coverAddress: AddressDto? = null,
    @SerializedName("download_addr")
    val downloadAddress: AddressDto? = null,
    @SerializedName("duration")
    val duration: Long? = null,
    @SerializedName("dynamic_cover")
    val dynamicCoverAddress: AddressDto? = null,
    @SerializedName("play_addr")
    val playAddress: AddressDto? = null
) {
    val url get() = playAddress?.url
    val size get() = playAddress?.dataSize
    val coverUrl get() = dynamicCoverAddress?.url
}