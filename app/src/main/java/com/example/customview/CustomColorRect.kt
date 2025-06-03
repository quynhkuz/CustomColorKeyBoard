package com.example.customview

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//vẽ màu hình vuông


@Composable
fun RunningSquareExample() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Phiên bản cơ bản
        ColorRunningSquare(
            modifier = Modifier.size(150.dp),
            durationMillis = 2000
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Phiên bản nâng cao
        AdvancedColorRunningSquare(
            modifier = Modifier.size(200.dp),
            colors = listOf(
                Color(0xFFFF5722), // Deep Orange
                Color(0xFFFFEB3B), // Yellow
                Color(0xFF4CAF50), // Green
                Color(0xFF2196F3), // Blue
                Color(0xFF9C27B0)  // Purple
            ),
            durationMillis = 4000,
            strokeWidth = 20.dp,
            trailLength = 0.4f
        )
    }
}


@Composable
fun AdvancedColorRunningSquare(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        Color(0xFFFF0000), // Red
        Color(0xFFFFFF00), // Yellow
        Color(0xFF00FF00), // Green
        Color(0xFF00FFFF), // Cyan
        Color(0xFF0000FF), // Blue
        Color(0xFFFF00FF)  // Magenta
    ),
    durationMillis: Int = 3000,
    strokeWidth: Dp = 16.dp,
    cornerRadius: Float = 0f, // 0 = vuông, > 0 = bo góc
    trailLength: Float = 0.3f // Độ dài đoạn màu chạy (0-1)
) {
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier) {
        val canvasSize = size.minDimension
        val strokeWidthPx = strokeWidth.toPx()
        val padding = strokeWidthPx / 2
        val innerSize = canvasSize - strokeWidthPx

        // Tính toán các điểm trên hình vuông
        val points = listOf(
            // Top edge
            Offset(padding, padding) to Offset(canvasSize - padding, padding),
            // Right edge
            Offset(canvasSize - padding, padding) to Offset(canvasSize - padding, canvasSize - padding),
            // Bottom edge
            Offset(canvasSize - padding, canvasSize - padding) to Offset(padding, canvasSize - padding),
            // Left edge
            Offset(padding, canvasSize - padding) to Offset(padding, padding)
        )

        // Tạo brush gradient
        val brush = Brush.linearGradient(
            colors = colors + colors.take(1),
            start = Offset.Zero,
            end = Offset(canvasSize, canvasSize)
        )

        // Vẽ từng đoạn
        for (i in 0 until 4) {
            val edgeProgress = (progress * 4 - i).coerceIn(0f, 1f)
            if (edgeProgress > 0) {
                val (start, end) = points[i]

                // Tính toán đoạn màu chạy
                val trailStart = (edgeProgress - trailLength).coerceAtLeast(0f)
                val trailEnd = edgeProgress

                if (trailStart < trailEnd) {
                    val segmentStart = Offset(
                        start.x + (end.x - start.x) * trailStart,
                        start.y + (end.y - start.y) * trailStart
                    )

                    val segmentEnd = Offset(
                        start.x + (end.x - start.x) * trailEnd,
                        start.y + (end.y - start.y) * trailEnd
                    )

                    drawLine(
                        brush = brush,
                        start = segmentStart,
                        end = segmentEnd,
                        strokeWidth = strokeWidthPx,
                        cap = StrokeCap.Round
                    )
                }
            }
        }

        // Vẽ bo góc nếu có
        if (cornerRadius > 0) {
            // Cần thêm code xử lý bo góc ở đây
        }
    }
}



@Composable
fun ColorRunningSquare(
    modifier: Modifier = Modifier,
    durationMillis: Int = 2000
) {
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val colors = listOf(
        Color.Red,
        Color.Yellow,
        Color.Green,
        Color.Blue,
        Color.Magenta,
        Color.Red // Lặp lại màu đầu để liền mạch
    )

    Canvas(modifier = modifier.size(200.dp)) {
        val squareSize = size.minDimension
        val strokeWidth = squareSize * 0.1f
        val padding = strokeWidth / 2

        // Tính toán vị trí bắt đầu dựa trên progress
        val startPoint = progress * 4 // 4 cạnh hình vuông

        // Tạo brush với gradient màu
        val brush = Brush.sweepGradient(
            colors = colors,
            center = center
        )

        // Vẽ từng đoạn của hình vuông
        for (i in 0 until 4) {
            val segmentProgress = (startPoint - i).coerceIn(0f, 1f)
            if (segmentProgress > 0) {
                val startCorner = when (i) {
                    0 -> Offset(padding, padding) // Top-left
                    1 -> Offset(squareSize - padding, padding) // Top-right
                    2 -> Offset(squareSize - padding, squareSize - padding) // Bottom-right
                    else -> Offset(padding, squareSize - padding) // Bottom-left
                }

                val endCorner = when (i) {
                    0 -> Offset(squareSize - padding, padding) // Top edge
                    1 -> Offset(squareSize - padding, squareSize - padding) // Right edge
                    2 -> Offset(padding, squareSize - padding) // Bottom edge
                    else -> Offset(padding, padding) // Left edge
                }

                drawLine(
                    brush = brush,
                    start = startCorner,
                    end = Offset(
                        startCorner.x + (endCorner.x - startCorner.x) * segmentProgress,
                        startCorner.y + (endCorner.y - startCorner.y) * segmentProgress
                    ),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}