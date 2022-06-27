package com.altaie.data.models.tiktok


import com.google.gson.annotations.SerializedName

data class TickTokPost(
    @SerializedName("aweme_detail")
    val awesomeDetail: AwesomeDetail? = null,
    @SerializedName("status_code")
    val statusCode: Int? = null
) {
    data class AwesomeDetail(
        @SerializedName("author")
        val author: Author? = null,
        @SerializedName("aweme_id")
        val awesomeId: String? = null,
        @SerializedName("create_time")
        val createTime: Int? = null,
        @SerializedName("desc")
        val desc: String? = null,
        @SerializedName("music")
        val music: Music? = null,
        @SerializedName("prevent_download")
        val preventDownload: Boolean? = null,
        @SerializedName("region")
        val region: String? = null,
        @SerializedName("share_url")
        val shareUrl: String? = null,
        @SerializedName("statistics")
        val statistics: Statistics? = null,
        @SerializedName("video")
        val video: Video? = null
    )
}