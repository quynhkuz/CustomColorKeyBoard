package com.example.customview

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AdvancedColorWheel(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        Color(0xFFFF0000), // Red
        Color(0xFFFFFF00), // Yellow
        Color(0xFF00FF00), // Green
        Color(0xFF00FFFF), // Cyan
        Color(0xFF0000FF), // Blue
        Color(0xFFFF00FF)  // Magenta
    ),
    durationMillis: Int = 2000,
    strokeWidth: Dp = 0.dp,
    strokeColor: Color = Color.Transparent
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val radius = (size.minDimension / 2) - strokeWidth.toPx()/2
            val center = Offset(size.width / 2, size.height / 2)

            // Vẽ các phần màu
            colors.forEachIndexed { index, color ->
                val startAngle = index * (360f / colors.size) + rotationAngle
                val sweepAngle = 360f / colors.size

                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = center - Offset(radius, radius),
                    size = Size(radius * 2, radius * 2)
                )
            }

            // Vẽ viền nếu có
            if (strokeWidth > 0.dp) {
                drawCircle(
                    color = strokeColor,
                    radius = radius,
                    center = center,
                    style = Stroke(width = strokeWidth.toPx())
                )
            }
        }
    }
}

@Composable
fun AdvancedExample() {
    AdvancedColorWheel(
        modifier = Modifier.size(250.dp),
        colors = listOf(
            Color(0xFFFF5722), // Deep Orange
            Color(0xFFFFEB3B), // Yellow
            Color(0xFF4CAF50), // Green
            Color(0xFF2196F3), // Blue
            Color(0xFF9C27B0)  // Purple
        ),
        durationMillis = 4000,
        strokeWidth = 4.dp,
        strokeColor = Color.Black
    )
}




//vẽ màu hình tròn xoay
@Composable
fun ColorWheelExample() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        RotatingColorWheel(
            modifier = Modifier.size(200.dp),
            durationMillis = 3000
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Hình tròn nhiều màu đang xoay")
    }
}

@Composable
fun RotatingColorWheel(
    modifier: Modifier = Modifier,
    durationMillis: Int = 2000
) {
    // Tạo animation vô hạn cho góc xoay
    val infiniteTransition = rememberInfiniteTransition()
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Danh sách màu sắc cho hình tròn
    val colors = listOf(
        Color.Red,
        Color.Yellow,
        Color.Green,
        Color.Blue,
        Color.Magenta,
        Color.Cyan
    )

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val radius = size.minDimension / 2
            val center = Offset(size.width / 2, size.height / 2)

            // Vẽ từng phần hình tròn với màu khác nhau
            colors.forEachIndexed { index, color ->
                val startAngle = index * (360f / colors.size) + rotationAngle
                val sweepAngle = 360f / colors.size

                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = center - Offset(radius, radius),
                    size = Size(radius * 2, radius * 2)
                )

//                drawRect(
//                    color = color,
//                    topLeft = center - Offset(radius, radius),
//                    size = Size(radius * 2, radius * 2)
//                )
            }
        }
    }
}













