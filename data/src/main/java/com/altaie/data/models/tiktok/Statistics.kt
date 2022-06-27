package com.altaie.data.models.tiktok


import com.google.gson.annotations.SerializedName

data class Statistics(
    @SerializedName("aweme_id")
    val awesomeId: String? = null,
    @SerializedName("collect_count")
    val collectCount: Int? = null,
    @SerializedName("comment_count")
    val commentCount: Int? = null,
    @SerializedName("digg_count")
    val diggCount: Int? = null,
    @SerializedName("download_count")
    val downloadCount: Int? = null,
    @SerializedName("forward_count")
    val forwardCount: Int? = null,
    @SerializedName("lose_comment_count")
    val loseCommentCount: Int? = null,
    @SerializedName("lose_count")
    val loseCount: Int? = null,
    @SerializedName("play_count")
    val playCount: Int? = null,
    @SerializedName("share_count")
    val shareCount: Int? = null,
    @SerializedName("whatsapp_share_count")
    val whatsappShareCount: Int? = null
)