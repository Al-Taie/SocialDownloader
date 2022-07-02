package com.altaie.socialdownloader.utils

import android.app.DownloadManager
import android.database.Cursor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.atomic.AtomicBoolean

class DownloadStateRetriever(private val downloadManager: DownloadManager) {
    fun retrieve(id: Long) = flow {
        val downloading = AtomicBoolean(true)
        val isPending = AtomicBoolean(false)

        while (downloading.get()) {
            val query = DownloadManager.Query().setFilterById(id)
            val cursor = downloadManager.query(query)

            cursor.moveToFirst()

            val bytesDownloaded = cursor.intValue(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
            val bytesTotal = cursor.intValue(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)

            if (isSuccessful(cursor)) downloading.set(false)

            if (isFailed(cursor)) {
                downloading.set(false)
                emit(DownloadingState.Failed)
            }

            if (isCanceled(cursor)) {
                downloading.set(false)
                emit(DownloadingState.Canceled)
            }

            if (isPending(cursor) && !isPending.get()) {
                isPending.set(true)
                emit(DownloadingState.Pending)
            }

            cursor.close()

            emit(DownloadingState.Downloading(bytesDownloaded, bytesTotal))

            if (downloading.get()) delay(150)
        }
    }.flowOn(Dispatchers.IO)

    private fun isSuccessful(cursor: Cursor) = status(cursor) == DownloadManager.STATUS_SUCCESSFUL

    private fun isFailed(cursor: Cursor) = status(cursor) == DownloadManager.STATUS_FAILED

    private fun isPending(cursor: Cursor) = status(cursor) == DownloadManager.STATUS_PENDING

    private fun isCanceled(cursor: Cursor) = status(cursor) == 0

    private fun status(cursor: Cursor) = cursor.intValue(DownloadManager.COLUMN_STATUS)

    sealed class DownloadingState {
        data class Downloading(val downloadedBytes: Int, val totalBytes: Int) : DownloadingState() {
            val progress = if (totalBytes == 0) 0 else ((downloadedBytes * 100) / totalBytes)
        }
        object Failed : DownloadingState()
        object Pending : DownloadingState()
        object Canceled : DownloadingState()
    }
}
