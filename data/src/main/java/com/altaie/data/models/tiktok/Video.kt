package com.altaie.data.models.tiktok


import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("cover")
    val coverAddress: Address? = null,
    @SerializedName("download_addr")
    val downloadAddress: Address? = null,
    @SerializedName("duration")
    val duration: Int? = null,
    @SerializedName("dynamic_cover")
    val dynamicCoverAddress: Address? = null,
    @SerializedName("play_addr")
    val playAddress: Address? = null
)