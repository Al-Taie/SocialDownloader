package com.altaie.socialdownloader.ui.common

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.EventListener
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun DynamicImage(
    modifier: Modifier = Modifier,
    url: String?,
    shape: Shape = MaterialTheme.shapes.large,
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28)
                add(ImageDecoderDecoder.Factory())
            else
                add(GifDecoder.Factory())
        }.eventListener(object : EventListener {
            override fun onStart(request: ImageRequest) {
                url?.let { isLoading = true }
            }

            override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                isLoading = false
            }
        })
        .crossfade(true)
        .build()

    AsyncImage(
        model = url,
        imageLoader = imageLoader,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(shape)
            .placeholder(
                visible = isLoading,
                shape = shape,
                highlight = PlaceholderHighlight.shimmer(),
            ),
    )
}