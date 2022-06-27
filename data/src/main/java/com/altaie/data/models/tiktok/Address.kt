package com.altaie.data.models.tiktok


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("data_size")
    val dataSize: Int? = null,
    @SerializedName("uri")
    val uri: String? = null,
    @SerializedName("url_list")
    val urlList: List<String?>? = null
)