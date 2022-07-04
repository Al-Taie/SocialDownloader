package com.altaie.data.models.tiktok


import com.google.gson.annotations.SerializedName

data class StatisticsDto(
    @SerializedName("aweme_id")
    val awesomeId: String? = null,
    @SerializedName("collect_count")
    val collectCount: Long? = null,
    @SerializedName("comment_count")
    val commentCount: Long? = null,
    @SerializedName("digg_count")
    val diggCount: Long? = null,
    @SerializedName("download_count")
    val downloadCount: Long? = null,
    @SerializedName("forward_count")
    val forwardCount: Long? = null,
    @SerializedName("lose_comment_count")
    val loseCommentCount: Long? = null,
    @SerializedName("lose_count")
    val loseCount: Long? = null,
    @SerializedName("play_count")
    val playCount: Long? = null,
    @SerializedName("share_count")
    val shareCount: Long? = null,
    @SerializedName("whatsapp_share_count")
    val whatsappShareCount: Long? = null
)