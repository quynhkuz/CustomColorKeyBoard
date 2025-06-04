package com.example.customview

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LedRunningAlphabet(
    backgroundImage: Painter,
    modifier: Modifier = Modifier
) {
    val alphabet = ('A'..'Z').toList()

    // Biến lưu chiều rộng tổng (của grid hoặc container)
    var containerWidth by remember { mutableFloatStateOf(0f) }

    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        0f, 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing)
        )
    )

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = backgroundImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(6),
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { containerWidth = it.width.toFloat() },
            contentPadding = PaddingValues(16.dp)
        ) {
            items(alphabet.size) { index ->
                LetterWithRunningGradient(
                    letter = alphabet[index],
                    index = index,
                    totalCount = alphabet.size,
                    progress = progress,
                    containerWidth = containerWidth,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(60.dp)
                )
            }
        }
    }
}

@Composable
fun LetterWithRunningGradient(
    letter: Char,
    index: Int,
    totalCount: Int,
    progress: Float,
    containerWidth: Float,
    modifier: Modifier = Modifier
) {
    Text(
        text = letter.toString(),
        fontSize = 64.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier.drawWithCache {
            // Chiều dài gradient dài gấp containerWidth để chạy mượt
            val gradientLength = containerWidth * 3f

            // Tính offset dịch chuyển theo progress
            val animatedX = gradientLength * progress

            // Tính vị trí bắt đầu cho từng chữ để tránh trùng màu
            val letterPosition = (index.toFloat() / totalCount) * gradientLength

            val gradientBrush = Brush.linearGradient(
                colors = listOf(
                    Color.Red, Color.Yellow, Color.Green, Color.Cyan, Color.Blue, Color.Magenta, Color.Red
                ),
                start = Offset(x = letterPosition + animatedX - gradientLength, y = 0f),
                end = Offset(x = letterPosition + animatedX, y = size.height)
            )

            onDrawWithContent {
                drawIntoCanvas { canvas ->
                    canvas.saveLayer(bounds = size.toRect(), paint = Paint())
                    drawContent()
                    drawRect(brush = gradientBrush, blendMode = BlendMode.SrcIn)
                    canvas.restore()
                }
            }
        }
    )
}
