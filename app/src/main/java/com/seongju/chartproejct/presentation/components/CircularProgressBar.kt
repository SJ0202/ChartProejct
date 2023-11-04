package com.seongju.chartproejct.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seongju.chartproejct.ui.theme.CircleBackgroundColor

/**
 * Circular progress bar
 *
 * @param size circle size
 * @param value target value
 * @param indicatorThickness
 * @param animationDuration
 * @param animationDelay
 * @param backgroundIndicatorColor color
 * @param foregroundIndicatorColor gradient brush
 */
@Composable
fun CircularProgressBar(
    size: Dp,
    value: Float,
    maxValue: Float,
    indicatorThickness: Dp = 28.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
    backgroundIndicatorColor: Color = CircleBackgroundColor,
    foregroundIndicatorColor: Brush = Brush.linearGradient(
        colors = listOf(
            Color(0xff48c6ef),
            Color(0xff6f86d6)
        ),
        start = Offset.Zero,
        end = Offset.Infinite
    ),
) {
    val circleSize = size / 1.2f
    val animationValue = remember { Animatable(0f) }

    // change to value animation
    LaunchedEffect(key1 = value) {
        animationValue.animateTo(
            targetValue = value,
            animationSpec = tween(
                durationMillis = animationDuration,
                delayMillis = animationDelay
            )
        )
    }

    Box(
        modifier = Modifier
            .size(size = size),
        contentAlignment = Alignment.Center
    ) {
        // Circle draw
        Canvas(
            modifier = Modifier
                .size(size = circleSize),
        ) {
            // Background circle
            drawCircle(
                color = backgroundIndicatorColor,
                radius = circleSize.toPx() / 2,
                style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round)
            )

            // Foreground circle
            drawArc(
                brush = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = (animationValue.value / maxValue) * 360,
                useCenter = false,
                style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round)
            )
        }

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = animationValue.value.toInt().toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp
            )
            Spacer(
                modifier = Modifier
                    .width(size / 2)
                    .height(1.dp)
                    .background(Color.LightGray)
            )
            Text(
                text = maxValue.toInt().toString(),
                fontSize = 20.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CircularProgressBarPreview() {

    val circleValue = remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Circular chart
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Title
            Text(
                text = "STRESS",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )

            // CircularProgressBar
            CircularProgressBar(
                size = 180.dp,
                value = circleValue.value,
                maxValue = 150f,
                foregroundIndicatorColor = Brush.linearGradient(
                    colors = listOf(
                        Color(0xff48c6ef),
                        Color(0xff6f86d6)
                    ),
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            )
        }

        // Spacer
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
        // Value change event
        Button(onClick = {
            // 1..100 random number
            circleValue.value = (1..150).random().toFloat()
        }) {
            Text(text = "Change")
        }
    }
}