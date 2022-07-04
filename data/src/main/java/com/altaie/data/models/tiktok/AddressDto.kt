package com.altaie.data.models.tiktok


import com.google.gson.annotations.SerializedName

data class AddressDto(
    @SerializedName("data_size")
    val dataSize: Long? = null,
    @SerializedName("url_list")
    private val urlList: List<String?>? = null
) {
    val url: String? get() = urlList?.last()
}