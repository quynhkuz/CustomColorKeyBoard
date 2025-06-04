package com.example.customview

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.radialGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt


@Composable
fun KeyboardWithColorWaveText() {
    val keyboardRows = listOf(
        listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
        listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
        listOf("Z", "X", "C", "V", "B", "N", "M"),
        listOf("SPACE")
    )

    val infiniteTransition = rememberInfiniteTransition()
    val waveProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val colors = listOf(
        Color(0xFFFF5555), // Light Red
        Color(0xFFFFAA00), // Orange
        Color(0xFFFFFF55), // Light Yellow
        Color(0xFF55FF55), // Light Green
        Color(0xFF5555FF), // Light Blue
        Color(0xFFAA00FF), // Purple
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        keyboardRows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                row.forEachIndexed { index, key ->
                    // Tính toán màu dựa trên vị trí và tiến trình wave
                    val colorIndex = ((waveProgress * colors.size * 2) + index) % colors.size
                    val color = colors[colorIndex.toInt()]

                    val isSpace = key == "SPACE"
                    val keySize = if (isSpace) 120.dp else 40.dp

                    Box(
                        modifier = Modifier
                            .size(keySize)
                            .padding(4.dp)
                            .background(Color.DarkGray, RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = key,
                            color = color,
                            fontSize = if (isSpace) 16.sp else 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun KeyboardDemoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111111))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bàn Phím LED",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        KeyboardWithColorWaveText()
    }
}


//anima color keyboard
@Composable
fun SmoothColorWaveKeyboard() {
    val keyboardRows = listOf(
        listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
        listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
        listOf("Z", "X", "C", "V", "B", "N", "M"),
        listOf("⌂", "⇧", "␣", "↵", "⌫") // Các phím chức năng
    )

    val infiniteTransition = rememberInfiniteTransition()
    val wavePosition by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Dải màu gradient
    val colorStops = arrayOf(
        0.0f to Color(0xFFFF5555), // Red
        0.2f to Color(0xFFFFFF55), // Yellow
        0.4f to Color(0xFF55FF55), // Green
        0.6f to Color(0xFF5555FF), // Blue
        0.8f to Color(0xFFFF55FF), // Pink
        1.0f to Color(0xFFFF5555)  // Quay lại Red
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF222222))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tính toán tổng số phím để xác định vị trí tương đối
        val totalKeys = keyboardRows.sumOf { it.size }
        var keyIndex = 0

        keyboardRows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                row.forEach { key ->
                    // Tính vị trí tương đối của phím (0..1)
                    val keyPosition = keyIndex.toFloat() / totalKeys
                    keyIndex++

                    // Tính màu dựa trên vị trí sóng và vị trí phím
                    val colorProgress = (wavePosition + keyPosition) % 1f
                    val color = lerpColorStops(colorStops, colorProgress)

                    KeyboardKey(
                        key = key,
                        color = color,
                        isSpecialKey = key.length > 1
                    )
                }
            }
        }
    }
}

@Composable
fun KeyboardKey(
    key: String,
    color: Color,
    isSpecialKey: Boolean
) {
    Box(
        modifier = Modifier
            .size(if (isSpecialKey) 60.dp else 48.dp)
            .padding(4.dp)
            .background(Color(0xFF444444), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF666666), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = key,
            color = color,
            fontSize = if (isSpecialKey) 14.sp else 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(4.dp)
        )
    }
}

// Hàm trộn màu gradient
fun lerpColorStops(colorStops: Array<Pair<Float, Color>>, progress: Float): Color {
    if (progress <= colorStops.first().first) return colorStops.first().second
    if (progress >= colorStops.last().first) return colorStops.last().second

    val stop1 = colorStops.last { it.first <= progress }
    val stop2 = colorStops.first { it.first >= progress }

    if (stop1 == stop2) return stop1.second

    val localProgress = (progress - stop1.first) / (stop2.first - stop1.first)
    return lerp(stop1.second, stop2.second, localProgress)
}

@Composable
fun KeyboardDemo2() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "BÀN PHÍM RGB",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            SmoothColorWaveKeyboard()
        }
    }
}


//anima color keyboard 2
@Composable
fun RadialColorWaveKeyboard() {
    val keyboardRows = listOf(
        listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
        listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
        listOf("Z", "X", "C", "V", "B", "N", "M"),
        listOf("Ctrl", "Win", "Alt", "Space", "Alt", "Fn", "Ctrl")
    )

    val infiniteTransition = rememberInfiniteTransition()
    val waveProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Màu sắc tỏa ra từ trung tâm
    val colors = listOf(
        Color(0xFFFF00FF), // Magenta
        Color(0xFF00FFFF), // Cyan
        Color(0xFFFFFF00), // Yellow
        Color(0xFFFF0000), // Red
        Color(0xFF00FF00)  // Green
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF111111))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Xác định tâm bàn phím
        val centerX = keyboardRows[0].size / 2f
        val centerY = keyboardRows.size / 2f

        Column {
            keyboardRows.forEachIndexed { rowIndex, row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    row.forEachIndexed { colIndex, key ->
                        // Tính khoảng cách từ phím đến trung tâm
                        val distanceFromCenter = sqrt(
                            (colIndex - centerX).pow(2) +
                                    (rowIndex - centerY).pow(2)
                        )

                        // Tính màu dựa trên khoảng cách và tiến trình
                        val colorProgress = (waveProgress + distanceFromCenter * 0.2f) % 1f
                        val colorIndex = (colorProgress * colors.size).toInt() % colors.size
                        val color = colors[colorIndex]

                        KeyboardKey(
                            key = key,
                            textColor = color,
                            isSpecialKey = key.length > 1,
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun KeyboardKey(
    key: String,
    textColor: Color,
    isSpecialKey: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(if (isSpecialKey) 60.dp else 40.dp)
            .background(Color(0xFF333333), RoundedCornerShape(6.dp))
            .border(1.dp, Color(0xFF555555), RoundedCornerShape(6.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = key,
            color = textColor,
            fontSize = if (isSpecialKey) 14.sp else 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


//anim color Keyboard 333
@Composable
fun RadialSmoothColorKeyboard() {
    val keyboardRows = listOf(
        listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
        listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
        listOf("Z", "X", "C", "V", "B", "N", "M"),
        listOf("Ctrl", "⌂", "Alt", "␣", "Alt", "Fn", "Ctrl")
    )

    val infiniteTransition = rememberInfiniteTransition()
    val waveProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(), // Sử dụng chu kỳ 2π radian
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Gradient màu dạng vòng tròn màu sắc
    val colorStops = listOf(
        0.0f to Color(0xFFFF0080), // Pink
        0.2f to Color(0xFFFF00FF), // Magenta
        0.4f to Color(0xFF8000FF), // Purple
        0.6f to Color(0xFF0080FF), // Blue
        0.8f to Color(0xFF00FFFF), // Cyan
        1.0f to Color(0xFFFF0080)  // Quay lại Pink
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1A1A1A))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Tính toán tâm bàn phím (tọa độ tương đối)
        val centerX = (keyboardRows[0].size - 1) / 2f
        val centerY = (keyboardRows.size - 1) / 2f

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            keyboardRows.forEachIndexed { rowIndex, row ->
                Row(horizontalArrangement = Arrangement.Center) {
                    row.forEachIndexed { colIndex, key ->
                        // Tính góc và khoảng cách từ phím đến tâm
                        val dx = colIndex - centerX
                        val dy = rowIndex - centerY
                        val distance = sqrt(dx * dx + dy * dy)
                        val angle = atan2(dy, dx) // Góc trong khoảng -π đến π

                        // Tính màu dựa trên góc và tiến trình animation
                        val normalizedAngle =
                            (angle + PI).toFloat() / (2f * PI.toFloat()) // Chuẩn hóa về 0-1
                        val colorProgress =
                            (waveProgress / (2f * PI.toFloat()) + normalizedAngle + distance * 0.05f) % 1f
                        val color = lerpGradient(colorStops, colorProgress)

                        // Hiệu ứng phát sáng cho phím gần tâm
                        val glowIntensity = 1f - (distance * 0.15f).coerceIn(0f, 0.8f)

                        KeyboardKey(
                            key = key,
                            textColor = color,
                            glowColor = color.copy(alpha = glowIntensity * 0.3f),
                            isSpecialKey = key.length > 1,
                            modifier = Modifier.padding(3.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun KeyboardKey(
    key: String,
    textColor: Color,
    glowColor: Color = Color.Transparent,
    isSpecialKey: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(if (isSpecialKey) 56.dp else 42.dp)
            .background(Color(0xFF2D2D2D), RoundedCornerShape(6.dp))
            .border(1.dp, Color(0xFF444444), RoundedCornerShape(6.dp))
            .graphicsLayer {
                shadowElevation = if (glowColor != Color.Transparent) 8f else 0f
                shape = RoundedCornerShape(6.dp)
                clip = true
            }
            .drawWithContent {
                drawContent()
                if (glowColor != Color.Transparent) {
                    drawCircle(
                        color = glowColor,
                        radius = size.minDimension * 0.8f,
                        blendMode = BlendMode.Plus
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = key,
            color = textColor,
            fontSize = if (isSpecialKey) 14.sp else 16.sp,
            fontWeight = FontWeight.Bold,
            style = LocalTextStyle.current.copy(
                shadow = Shadow(
                    color = textColor.copy(alpha = 0.4f),
                    offset = Offset(0f, 0f),
                    blurRadius = 8f
                )
            )
        )
    }
}

// Hàm trộn màu gradient mượt mà
fun lerpGradient(colorStops: List<Pair<Float, Color>>, progress: Float): Color {
    val (prevStop, nextStop) = colorStops.zipWithNext().find {
        progress >= it.first.first && progress <= it.second.first
    } ?: return if (progress <= colorStops.first().first) colorStops.first().second
    else colorStops.last().second

    val localProgress = (progress - prevStop.first) / (nextStop.first - prevStop.first)
    return lerp(prevStop.second, nextStop.second, localProgress)
}


//anim color keyboard 444
@Composable
fun KeyboardWithMovingBackgroundText() {
    val keyboardRows = listOf(
        listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
        listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
        listOf("Z", "X", "C", "V", "B", "N", "M"),
        listOf("Ctrl", "Win", "Alt", "Space", "Alt", "Fn", "Ctrl")
    )

    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Màu sắc chuyển động
    val colors = listOf(
        Color(0xFFFF0000), // Red
        Color(0xFFFF00FF), // Magenta
        Color(0xFF0000FF), // Blue
        Color(0xFF00FFFF), // Cyan
        Color(0xFF00FF00), // Green
        Color(0xFFFFFF00), // Yellow
        Color(0xFFFF0000)  // Red (lặp lại)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Lớp nền chạy màu (ẩn bên dưới, chỉ hiện qua chữ)
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .align(Alignment.BottomCenter)
//                .drawWithContent {
//                    val gradientWidth = size.width * 2
//                    val offsetX = -size.width + progress * gradientWidth
//
//                    drawRect(
//                        brush = Brush.linearGradient(
//                            colors = colors,
//                            start = Offset(offsetX, 0f),
//                            end = Offset(offsetX + gradientWidth, 0f)
//                        ),
//                        size = size
//                    )
//                }
//        )

        // Bàn phím - chỉ chữ hiển thị màu chuyển động
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            keyboardRows.forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { key ->
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(if (key.length > 2) 60.dp else 48.dp)
                                .background(Color.Black, RoundedCornerShape(6.dp)) // Nền đen
                                .border(1.dp, Color(0x44FFFFFF), RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = key,
                                color = Color.White, // Màu chữ trắng (sẽ bị ghi đè bởi blend mode)
                                fontSize = if (key.length > 2) 14.sp else 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .graphicsLayer {
                                        alpha = 0.99f
                                        compositingStrategy = CompositingStrategy.Offscreen
                                    }
                                    .drawWithContent {
                                        // 1. Vẽ chữ trắng lên layer
                                        drawContent()

                                        // 2. Áp dụng gradient màu CHỈ lên phần chữ (dùng SrcIn)
                                        drawRect(
                                            brush = Brush.linearGradient(
                                                colors = colors,
                                                start = Offset(
                                                    -size.width + progress * size.width * 2,
                                                    0f
                                                ),
                                                end = Offset(progress * size.width * 2, 0f)
                                            ),
                                            blendMode = BlendMode.SrcIn // Chỉ giữ lại màu ở phần chữ
                                        )
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

//anim color keyboard 555
@Composable
fun KeyboardWithMovingBackgroundText222() {
    val keyboardRows = listOf(
        listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
        listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
        listOf("Z", "X", "C", "V", "B", "N", "M"),
        listOf("Ctrl", "Win", "Alt", "Space", "Alt", "Fn", "Ctrl")
    )

    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Màu sắc chuyển động
    val colors = listOf(
        Color(0xFFFF0000), // Red
        Color(0xFFFF00FF), // Magenta
        Color(0xFF0000FF), // Blue
        Color(0xFF00FFFF), // Cyan
        Color(0xFF00FF00), // Green
        Color(0xFFFFFF00), // Yellow
        Color(0xFF8BC34A),  // Red (lặp lại)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            keyboardRows.forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { key ->
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(if (key.length > 2) 60.dp else 48.dp)
                                .background(Color.Black, RoundedCornerShape(6.dp))
                                .drawWithContent {
                                    // Vẽ border chuyển động
                                    val borderWidth = 1.dp.toPx()
                                    val gradientWidth = size.width * 2
                                    val offsetX = -size.width + progress * gradientWidth

                                    drawContent()

                                    // Vẽ border gradient
                                    drawRoundRect(
                                        brush = Brush.linearGradient(
                                            colors = colors,
                                            start = Offset(offsetX, 0f),
                                            end = Offset(offsetX + gradientWidth, 0f)
                                        ),
                                        topLeft = Offset.Zero,
                                        size = size,
                                        cornerRadius = CornerRadius(6.dp.toPx()),
                                        style = Stroke(width = borderWidth)
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = key,
                                color = Color.White,
                                fontSize = if (key.length > 2) 14.sp else 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .graphicsLayer {
                                        alpha = 0.99f
                                        compositingStrategy = CompositingStrategy.Offscreen
                                    }
                                    .drawWithContent {
                                        drawContent()
                                        // Áp dụng gradient cho chữ
                                        drawRect(
                                            brush = Brush.linearGradient(
                                                colors = colors,
                                                start = Offset(
                                                    -size.width + progress * size.width * 2,
                                                    0f
                                                ),
                                                end = Offset(progress * size.width * 2, 0f)
                                            ),
                                            blendMode = BlendMode.SrcIn
                                        )
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}
