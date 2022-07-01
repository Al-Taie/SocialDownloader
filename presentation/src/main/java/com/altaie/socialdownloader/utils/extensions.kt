package com.altaie.socialdownloader.utils

import android.app.DownloadManager
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Context.DOWNLOAD_SERVICE
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


fun Uri?.toUrlOrNull() = this?.host?.plus(path)

fun String.noWhitespace() = filter { !it.isWhitespace() }

fun Calendar.strftime(format: String): String =
    SimpleDateFormat(format, Locale.getDefault()).format(this.time)

val Context.appName get() = applicationInfo.loadLabel(packageManager).toString()


fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

private fun Context.getExternalStorage(
    username: String,
    socialName: String,
    directory: String = Environment.DIRECTORY_MOVIES
): File {
    val path = Environment.getExternalStoragePublicDirectory(directory)
    val file = File(path, "$appName/$socialName/$username")
    if (!file.exists()) {
        file.mkdirs()
    }

    return file
}

enum class MediaExtension {
    IMAGE,
    AUDIO,
    VIDEO;

    fun getNameWithDate(date: String) =
        when (this) {
            IMAGE -> "IMG_$date.png"
            AUDIO -> "AUD_$date.mp3"
            VIDEO -> "VID_$date.mp4"
        }
}

private fun File.wrapWithDate(ext: MediaExtension = MediaExtension.VIDEO): File {
    val date = Calendar.getInstance().strftime("yyyyMMdd_HHmmss")
    val fileName = ext.getNameWithDate(date = date)
    return File(this, fileName)
}

fun Context.downloadManager(
    url: String?,
    username: String,
    socialName: String = "Tiktok",
    ext: MediaExtension = MediaExtension.VIDEO
): Flow<DownloadStateRetriever.DownloadingState> {
    if (url == null) {
        toast("No url provided!")
        return flow {}
    }

    val file = getExternalStorage(
        username = username,
        socialName = socialName
    ).wrapWithDate(ext = ext)

    val request = DownloadManager.Request(url.toUri())
        .setTitle(appName)
        .setDescription("Downloading Post from @$username on $socialName...")
        .setDestinationInExternalPublicDir(file.parent, file.name)
        .setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setAllowedOverMetered(true)

    val dm = (getSystemService(DOWNLOAD_SERVICE) as DownloadManager)
    val id = dm.enqueue(request)

    return DownloadStateRetriever(dm).retrieve(id)

}

fun Context.getFromClipBoard() : String? {
    val clipBoardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    return clipBoardManager.primaryClip?.getItemAt(0)?.text?.toString()
}

fun Cursor.column(which: String) = this.getColumnIndex(which)
fun Cursor.intValue(which: String): Int = this.getInt(column(which))
