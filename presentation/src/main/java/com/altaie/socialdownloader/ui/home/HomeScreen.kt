package com.altaie.socialdownloader.ui.home

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.EventListener
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.altaie.domain.models.Resources
import com.altaie.domain.models.tiktok.TikTokPost
import com.altaie.socialdownloader.ui.common.CircleIndicator
import com.altaie.socialdownloader.ui.theme.size
import com.altaie.socialdownloader.utils.DownloadStateRetriever.DownloadingState
import com.altaie.socialdownloader.utils.MediaExtension
import com.altaie.socialdownloader.utils.downloadManager
import com.altaie.socialdownloader.utils.noWhitespace
import com.altaie.socialdownloader.utils.toast
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), data: String? = null) {
    var url by remember { mutableStateOf(data ?: "").also { viewModel.onEvent(it.value) } }
    val validateUrlState by remember { viewModel.validateUrlState }
    var downloadProgress by remember { mutableStateOf(0) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(
            start = MaterialTheme.size.medium,
            end = MaterialTheme.size.medium,
            top = MaterialTheme.size.large,
            bottom = MaterialTheme.size.large,
        )
    ) {
        if (data == null) {
            TextField(
                value = url,
                singleLine = true,
                onValueChange = { value ->
                    url = value.noWhitespace()
                    viewModel.onEvent(value)
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { viewModel.onEvent(url) }),
                placeholder = { Text("url", modifier = Modifier.alpha(.5f)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large)
            )
        }

        if (!validateUrlState.isNullOrEmpty() && url.isNotEmpty()) {
            Spacer(modifier = Modifier.height(MaterialTheme.size.small))

            Text(
                text = validateUrlState.toString(),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (data == null)
            Spacer(modifier = Modifier.height(MaterialTheme.size.large))

        Box(contentAlignment = Alignment.Center) {
            Post(data = viewModel.post.value.toData ?: TikTokPost()) { progress ->
                downloadProgress = progress

                if (progress == 100) {
                    context.toast("Downloaded Successfully")
                }
            }

            if (viewModel.post.value is Resources.Loading)
                CircularProgressIndicator(modifier = Modifier.padding(bottom = MaterialTheme.size.large - 3.dp))

            CircleIndicator(
                value = downloadProgress,
                percentageEnabled = false,
                modifier = Modifier.padding(bottom = MaterialTheme.size.large - 3.dp)
            )
        }
    }
}

@Composable
fun Post(modifier: Modifier = Modifier, data: TikTokPost, downloadProgress: (Int) -> Unit) {
    var blur by remember { mutableStateOf(0f) }
    var opacity by remember { mutableStateOf(0f) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.large)
                .blur(blur.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            PostImage(
                url = data.coverUrl,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.large),
            )

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
                TextIcon(text = data.shares, icon = Icons.Outlined.Send)
                TextIcon(text = data.comments, icon = Icons.Outlined.ChatBubbleOutline)
                TextIcon(text = data.likes, icon = Icons.Outlined.FavoriteBorder)
                TextIcon(text = data.downloads, icon = Icons.Outlined.FileDownload)
                TextIcon(text = data.views, icon = Icons.Outlined.PlayArrow)
                TextIcon(text = data.saved, icon = Icons.Outlined.BookmarkBorder)
            }

            if (SDK_INT < Build.VERSION_CODES.S)
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.large),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = opacity)
                ) {}
        }

        FloatingActionButtonCustom(
            textVideoSize = data.videoSize,
            onAudioClicked = {
                coroutineScope.launch {
                    context.downloadManager(
                        url = data.audioUrl,
                        username = data.username,
                        socialName = data.socialName,
                        ext = MediaExtension.AUDIO
                    ).collect {
                        when (it) {
                            is DownloadingState.Downloading -> downloadProgress(it.progress)
                            is DownloadingState.Failed -> {
                                downloadProgress(0)
                                context.toast("Download Failed!")
                            }
                            is DownloadingState.Pending -> context.toast("Download Pending!")
                            is DownloadingState.Canceled -> {
                                downloadProgress(0)
                                context.toast("Download Canceled!")
                            }
                        }
                    }
                }
            },
            onVideoClicked = {
                coroutineScope.launch {
                    context.downloadManager(
                        url = data.videoUrl,
                        username = data.username,
                        socialName = data.socialName,
                    ).collect {
                        when (it) {
                            is DownloadingState.Downloading -> downloadProgress(it.progress)
                            is DownloadingState.Failed -> {
                                downloadProgress(0)
                                context.toast("Download Failed!")
                            }
                            is DownloadingState.Pending -> context.toast("Download Pending!")
                            is DownloadingState.Canceled -> {
                                downloadProgress(0)
                                context.toast("Download Canceled!")
                            }
                        }
                    }
                }
            }
        ) {
            blur = if (it) 7.dp.value else 0f
            opacity = if (it) .75f else 0f
        }
    }
}

@Composable
fun TextIcon(text: String, icon: ImageVector) {
    Column(
        modifier = Modifier.padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(24.dp)
                .padding(vertical = 2.dp)
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun FloatingActionButtonCustom(
    textVideoSize: String,
    onVideoClicked: () -> Unit = {},
    onAudioClicked: () -> Unit = {},
    onClicked: (value: Boolean) -> Unit = {}
) {
    var isFabClicked by remember { mutableStateOf(false) }
    var currentRotation by remember { mutableStateOf(0f) }
    val rotation = remember { Animatable(currentRotation) }

    fun reset() {
        currentRotation = if (isFabClicked) 0f else 180f
        isFabClicked = !isFabClicked
        onClicked(isFabClicked)
    }

    Column(horizontalAlignment = Alignment.End) {
        AnimatedVisibility(
            visible = isFabClicked,
            enter = slideInVertically() + fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically() + fadeOut()
        ) {
            Column(horizontalAlignment = Alignment.End) {
                FloatingActionTextButton(
                    text = textVideoSize,
                    imageVector = Icons.Outlined.Movie,
                    onClick = {
                        onVideoClicked()
                        reset()
                    }
                )

                FloatingActionTextButton(
                    text = "",
                    imageVector = Icons.Outlined.MusicVideo,
                    onClick = {
                        onAudioClicked()
                        reset()
                    }
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier.padding(bottom = 85.dp, end = 16.dp),
//            containerColor = MaterialTheme.colorScheme.surface,
            shape = CircleShape,
            onClick = ::reset
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowDownward,
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotation.value),
                contentDescription = null
            )

            LaunchedEffect(key1 = rotation, key2 = isFabClicked) {
                rotation.animateTo(currentRotation)
            }
        }
    }
}

@Composable
fun FloatingActionTextButton(
    text: String,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.height(56.dp)
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        FloatingActionButton(
            modifier = Modifier.padding(bottom = 16.dp),
            shape = CircleShape,
            onClick = onClick
        ) {
            Icon(
                imageVector = imageVector,
                modifier = Modifier
                    .size(24.dp),
                contentDescription = null
            )
        }
    }
}


@Composable
fun PostImage(
    modifier: Modifier = Modifier,
    url: String?,
    shape: Shape = MaterialTheme.shapes.large,
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28)
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

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    url: String?,
    username: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        PostImage(
            url = url,
            shape = CircleShape,
            modifier = modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.size.small))

        Text(
            text = username,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
