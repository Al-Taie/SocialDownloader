package com.altaie.socialdownloader.ui.home.common

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.altaie.domain.models.tiktok.TikTokPost
import com.altaie.socialdownloader.utils.DownloadStateRetriever
import com.altaie.socialdownloader.utils.MediaExtension
import com.altaie.socialdownloader.utils.downloadManager
import com.altaie.socialdownloader.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun DownloadActionButton(
    data: TikTokPost,
    downloadProgress: (Int) -> Unit = {},
    onClicked: (Boolean) -> Unit = {},
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Post Download Action Button
    FloatingActionButtonCustom(
        textVideoSize = data.videoSize,
        onClicked = onClicked,
        onAudioClicked = {
            handleDownload(
                context = context,
                coroutineScope = coroutineScope,
                downloadProgress = downloadProgress,
                downloadManagerFlow = context.downloadManager(
                    url = data.audioUrl,
                    username = data.username,
                    socialName = data.socialName,
                    ext = MediaExtension.AUDIO
                )
            )
        },
        onVideoClicked = {
            handleDownload(
                context = context,
                coroutineScope = coroutineScope,
                downloadProgress = downloadProgress,
                downloadManagerFlow = context.downloadManager(
                    url = data.videoUrl,
                    username = data.username,
                    socialName = data.socialName,
                    ext = MediaExtension.VIDEO
                )
            )
        },
    )
}

private fun handleDownload(
    context: Context,
    downloadManagerFlow: Flow<DownloadStateRetriever.DownloadingState>,
    coroutineScope: CoroutineScope,
    downloadProgress: (Int) -> Unit
) {
    coroutineScope.launch {
        downloadManagerFlow.collect {
            when (it) {
                is DownloadStateRetriever.DownloadingState.Downloading -> downloadProgress(it.progress)
                is DownloadStateRetriever.DownloadingState.Failed -> {
                    downloadProgress(0)
                    context.toast("Download Failed!")
                }
                is DownloadStateRetriever.DownloadingState.Pending -> context.toast("Download Pending!")
                is DownloadStateRetriever.DownloadingState.Canceled -> {
                    downloadProgress(0)
                    context.toast("Download Canceled!")
                }
            }
        }
    }
}