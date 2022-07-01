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

        while (downloading.get()) {
            val query = DownloadManager.Query().setFilterById(id)
            val cursor = downloadManager.query(query)

            cursor.moveToFirst()

            val bytesDownloaded = cursor.intValue(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
            val bytesTotal = cursor.intValue(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)

            if (isSuccessful(cursor)) downloading.set(false)

            if (isFailure(cursor)) {
                downloading.set(false)
                emit(DownloadingState.Failure)
            }

            cursor.close()

            emit(DownloadingState.Downloading(bytesDownloaded, bytesTotal))

            if (downloading.get()) delay(100)
        }
    }.flowOn(Dispatchers.IO)

    private fun isSuccessful(cursor: Cursor) = status(cursor) == DownloadManager.STATUS_SUCCESSFUL

    private fun isFailure(cursor: Cursor) = status(cursor) == DownloadManager.STATUS_FAILED

    private fun status(cursor: Cursor) = cursor.intValue(DownloadManager.COLUMN_STATUS)

    sealed class DownloadingState {
        data class Downloading(val downloadedBytes: Int, val totalBytes: Int) : DownloadingState() {
            val progress = if (totalBytes == 0) 0 else ((downloadedBytes * 100) / totalBytes)
        }
        object Failure : DownloadingState()
    }
}
