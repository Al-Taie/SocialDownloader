package com.altaie.socialdownloader.ui.common

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FloatingActionTextButton(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    text: String,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    val android12AndUp = SDK_INT >= VERSION_CODES.S

    Row(
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.height(56.dp)
        ) {
            Text(
                text = text,
                color = with(MaterialTheme.colorScheme) {
                    if (android12AndUp)
                        onPrimary
                    else
                        onPrimaryContainer
                },
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        FloatingActionButton(
            modifier = modifier.padding(bottom = 16.dp),
            shape = shape,
            onClick = onClick
        ) {
            Icon(
                imageVector = imageVector,
                modifier = iconModifier
                    .size(24.dp),
                contentDescription = null
            )
        }
    }
}
