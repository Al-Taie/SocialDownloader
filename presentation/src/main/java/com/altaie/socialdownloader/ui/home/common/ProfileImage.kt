package com.altaie.socialdownloader.ui.home.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.altaie.socialdownloader.ui.common.DynamicImage
import com.altaie.socialdownloader.ui.theme.size

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    url: String?,
    username: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Post Image
        DynamicImage(
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
