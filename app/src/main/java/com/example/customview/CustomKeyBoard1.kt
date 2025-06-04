package com.example.customview

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas

@Composable
fun LayeredEffect() {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Blue,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Layer 1: Background image (hiển thị toàn bộ)
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Layer 2: Text với hiệu ứng màu
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Vẽ màu chuyển động toàn màn hình
            drawRect(color = animatedColor)

            // Tạo mask từ text (chỉ giữ lại màu ở vùng text)
            drawIntoCanvas { canvas ->
                val paint = Paint().apply {
                    blendMode = BlendMode.DstIn
                }
                canvas.saveLayer(
                    bounds = Rect(Offset.Zero, size),
                    paint = paint
                )

                // Vẽ text như một mask
                drawContext.canvas.nativeCanvas.drawText(
                    "YOUR TEXT",
                    center.x - 150, // Điều chỉnh vị trí
                    center.y,
                    android.graphics.Paint().apply {
                        textSize = 100.sp.toPx()
                        color = android.graphics.Color.WHITE
                    }
                )

                canvas.restore()
            }
        }
    }
}












@Composable
fun LayeredEffect123() {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Blue,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Layer 1: Background image (hiển thị đầy đủ)
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Layer 2: Text với hiệu ứng màu
        Box(
            modifier = Modifier
                .wrapContentSize()
                .drawWithContent {
                    // Vẽ màu chuyển động như một layer riêng
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(animatedColor, animatedColor)
                        ),
                        blendMode = BlendMode.SrcIn
                    )
                    // Vẽ text
                    drawContent()
                }
        ) {
            Text(
                text = "YOUR TEXT",
                style = TextStyle(
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Màu chữ trắng
                ),
                modifier = Modifier
                    .align(Alignment.Center)
                    .graphicsLayer {
                        alpha = 0.99f
                        compositingStrategy = CompositingStrategy.Offscreen
                    }
            )
        }
    }
}