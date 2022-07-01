package com.altaie.socialdownloader.ui.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CircleIndicator(
    modifier: Modifier = Modifier,
    value: Int = 0,
    maxValue: Int = 100,
    startAngle: Float = 130f,
    sweepAngle: Float = 280f,
    foregroundColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.onSurface,
    strokeWidth: Float = 37.7f,
    percentageEnabled: Boolean = true,
) {
    var currentValue = value

    if (value > maxValue)
        currentValue = maxValue

    var animatedIndicatorValue by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = currentValue) { animatedIndicatorValue = currentValue.toFloat() }

    val percentage = (animatedIndicatorValue / maxValue)

    val sweepAngleForeground by animateFloatAsState(
        targetValue = sweepAngle * percentage,
        animationSpec = tween(durationMillis = 1000)
    )

    Column(
        modifier = modifier
            .width(150.dp)
            .height(150.dp)
            .drawBehind {
                circleIndicator(
                    color = backgroundColor.copy(alpha = .1f),
                    componentSize = size / 1.25f,
                    strokeWidth = strokeWidth,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle
                )
                circleIndicator(
                    color = foregroundColor,
                    componentSize = size / 1.25f,
                    strokeWidth = strokeWidth,
                    startAngle = startAngle,
                    sweepAngle = sweepAngleForeground
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (percentageEnabled)
            Text(
                text = "$currentValue", style = TextStyle(
                    fontSize = 28.sp,
                    color = backgroundColor.copy(alpha = percentage)
                )
            )
    }
}


fun DrawScope.circleIndicator(
    color: Color,
    componentSize: Size,
    strokeWidth: Float,
    startAngle: Float = 130f,
    sweepAngle: Float = 280f,
) {
    drawArc(
        color = color,
        size = componentSize,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        ),
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round
        )
    )
}

@Composable
@Preview(showBackground = true)
fun CircleIndicatorPreview() {
    CircleIndicator()
}