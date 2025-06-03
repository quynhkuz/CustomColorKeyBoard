package com.example.customview

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp


@Composable
fun LEDKeyboardEffect(
    modifier: Modifier = Modifier,
    durationMillis: Int = 2000
) {
    // Animation cho hiệu ứng chạy
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Layout bàn phím đơn giản (hàng phím)
    Column(modifier = modifier) {
        // Hàng phím số
        KeyboardRow(
            keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            progress = progress,
            rowIndex = 0
        )

        // Hàng phím QWERTY
        KeyboardRow(
            keys = listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
            progress = progress,
            rowIndex = 1
        )

        // Hàng phím ASDF
        KeyboardRow(
            keys = listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
            progress = progress,
            rowIndex = 2
        )

        // Hàng phím ZXCV
        KeyboardRow(
            keys = listOf("Z", "X", "C", "V", "B", "N", "M"),
            progress = progress,
            rowIndex = 3
        )
    }
}

@Composable
fun KeyboardRow(
    keys: List<String>,
    progress: Float,
    rowIndex: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        keys.forEachIndexed { index, key ->
            val totalKeys = keys.size
            val keyProgress = (progress * totalKeys * 2 - (index + rowIndex)).coerceIn(0f, 1f)

            val color = when {
                keyProgress < 0.2f -> Color.DarkGray
                keyProgress < 0.4f -> Color(0xFF00FF00) // Xanh lá
                keyProgress < 0.6f -> Color(0xFFFFFF00) // Vàng
                keyProgress < 0.8f -> Color(0xFF0B4AD5) // Cam
                else -> Color(0xFFFF0000) // Đỏ
            }

            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(color.copy(alpha = 0.7f))
            ) {
                // Hiển thị ký tự phím
                Text(
                    text = key,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun AdvancedLEDKeyboard(
    modifier: Modifier = Modifier,
    waveSpeed: Float = 1f,
    waveWidth: Float = 0.2f
) {
    val infiniteTransition = rememberInfiniteTransition()
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Màu sắc cho hiệu ứng LED
    val ledColors = listOf(
        Color(0xFF00FF00), // Xanh lá
        Color(0xFF8D4CBB), // Cyan
        Color(0xFF0000FF), // Xanh dương
        Color(0xFFFF9800), // Magenta
        Color(0xFFFF0000)  // Đỏ
    )

    Column(modifier = modifier) {
        // Các hàng phím
        listOf(
            listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
            listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
            listOf("Z", "X", "C", "V", "B", "N", "M")
        ).forEachIndexed { rowIndex, keys ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                keys.forEachIndexed { keyIndex, key ->
                    // Tính toán vị trí trong hiệu ứng sóng
                    val position = (keyIndex.toFloat() / keys.size) + (rowIndex * 0.1f)
                    val wavePosition = (position - waveOffset * waveSpeed).let {
                        if (it < 0) it + 1 else it
                    }

                    // Tính toán màu sắc dựa trên vị trí sóng
                    val color = when {
                        wavePosition < waveWidth -> {
                            val progressInWave = wavePosition / waveWidth
                            lerp(
                                ledColors.first(),
                                ledColors.last(),
                                progressInWave
                            )
                        }
                        else -> Color.DarkGray
                    }

                    LEDKey(
                        key = key,
                        color = color,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LEDKey(
    key: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(
                color = color.copy(alpha = 0.8f),
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = color.copy(alpha = 0.5f),
                shape = RoundedCornerShape(4.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = key,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}



@Composable
fun KeyboardDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Phiên bản cơ bản
        LEDKeyboardEffect(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            durationMillis = 3000
        )

        // Phiên bản nâng cao
        AdvancedLEDKeyboard(
            modifier = Modifier.fillMaxWidth(),
            waveSpeed = 1.5f,
            waveWidth = 0.3f
        )
    }
}





