package com.altaie.domain.models.tiktok


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("data_size")
    val dataSize: Long? = null,
    @SerializedName("url_list")
    private val urlList: List<String?>? = null
) {
    val url: String? get() = urlList?.last()
}