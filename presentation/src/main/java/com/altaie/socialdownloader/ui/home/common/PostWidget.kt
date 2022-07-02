package com.altaie.socialdownloader.ui.home.common

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.altaie.domain.models.tiktok.TikTokPost
import com.altaie.socialdownloader.ui.common.DynamicImage
import com.altaie.socialdownloader.ui.common.TextIcon

@Composable
fun PostWidget(modifier: Modifier = Modifier, data: TikTokPost, downloadProgress: (Int) -> Unit) {
    var blur by remember { mutableStateOf(0f) }
    var opacity by remember { mutableStateOf(0f) }

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.fillMaxSize()
    ) {

        // A container for the post image and the profile image and post stats
        Box(
            modifier = modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.large)
                .blur(blur.dp), // Works only on API 31+ (Android 12)
            contentAlignment = Alignment.BottomCenter
        ) {
            // Post Image
            DynamicImage(
                url = data.coverUrl,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.large),
            )

            // We use color with opacity in background to make image clear
            Box(contentAlignment = Alignment.Center) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.large),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = .4f)
                ) {}

                ProfileImage(
                    url = data.profileImageUrl,
                    username = data.username
                )
            }

            // Post Stats
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        Shapes.Full.copy(
                            topStart = CornerSize(2.dp),
                            topEnd = CornerSize(2.dp),
                            bottomStart = MaterialTheme.shapes.large.bottomStart,
                            bottomEnd = MaterialTheme.shapes.large.bottomEnd,
                        )
                    )
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = .6f)),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                with(Icons.Outlined) {
                    TextIcon(text = data.shares, icon = Send)
                    TextIcon(text = data.comments, icon = ChatBubbleOutline)
                    TextIcon(text = data.likes, icon = FavoriteBorder)
                    TextIcon(text = data.downloads, icon = FileDownload)
                    TextIcon(text = data.views, icon = PlayArrow)
                    TextIcon(text = data.saved, icon = BookmarkBorder)
                }
            }

            // Blur Not supported in Android less than API 31
            // So we use a normal color with opacity instead of blur
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.large),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = opacity)
                ) {}
            }
        }

        // Post Download Action Button
        DownloadActionButton(data = data, downloadProgress = downloadProgress) {
            blur = if (it) 7.dp.value else 0f
            opacity = if (it) .75f else 0f
        }
    }
}
