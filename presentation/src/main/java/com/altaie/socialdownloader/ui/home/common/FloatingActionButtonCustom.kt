package com.altaie.socialdownloader.ui.home.common

import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.MusicVideo
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.altaie.socialdownloader.ui.common.FloatingActionTextButton

@Composable
fun FloatingActionButtonCustom(
    modifier: Modifier = Modifier,
    textVideoSize: String,
    onVideoClicked: () -> Unit = {},
    onAudioClicked: () -> Unit = {},
    horizontalAlignment: Alignment.Horizontal = Alignment.End,
    onClicked: (value: Boolean) -> Unit = {},
) {
    var isFabClicked by remember { mutableStateOf(false) }
    var currentRotation by remember { mutableStateOf(0f) }
    val rotation = remember { Animatable(currentRotation) }

    fun reset() {
        currentRotation = if (isFabClicked) 0f else 180f
        isFabClicked = !isFabClicked
        onClicked(isFabClicked)
    }

    Column(horizontalAlignment = horizontalAlignment) {
        AnimatedVisibility(
            visible = isFabClicked,
            enter = slideInVertically() + fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically() + fadeOut()
        ) {
            Column(horizontalAlignment = horizontalAlignment) {
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
            modifier = modifier.padding(bottom = 85.dp, end = 16.dp),
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