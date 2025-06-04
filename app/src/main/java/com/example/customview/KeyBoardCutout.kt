package com.example.customview

import android.R.attr.textSize
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.AndroidPaint
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TextWithMaskedColor(
    backgroundImage: Painter,
    text: String,
    maskColor: Color,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        // Layer A - Ảnh nền
        Image(
            painter = backgroundImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Layer C + Layer B (text làm mask để lộ màu)
        Text(
            text = text,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center)
                .drawWithContent {
                    drawIntoCanvas { canvas ->
                        // Vẽ chữ trước
                        val paint = Paint()
                        paint.asFrameworkPaint().apply {
                            isAntiAlias = true
                            textSize = 150f
                            color = android.graphics.Color.BLACK
                        }

                        // Vẽ layer B (khung màu) chỉ ở nơi có chữ
                        canvas.saveLayer(bounds = size.toRect(), paint = Paint())
                        drawContent() // vẽ chữ
                        drawRect(
                            color = maskColor,
                            blendMode = BlendMode.SrcIn // chỉ vẽ layer B ở chỗ có content (text)
                        )
                        canvas.restore()
                    }
                }
        )
    }
}


@Composable
fun AnimatedGradientTextMask(
    backgroundImage: Painter,
    text: String,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()

    // Animate the offset for the gradient
    val animatedOffsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(modifier = modifier) {
        // Layer A - Background Image
        Image(
            painter = backgroundImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Layer B (gradient) + Layer C (text as mask)
        Text(
            text = text,
            fontSize = 64.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .align(Alignment.Center)
                .drawWithCache {
                    val gradientBrush = Brush.linearGradient(
                        colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Magenta),
                        start = Offset(animatedOffsetX, 0f),
                        end = Offset(animatedOffsetX + 400f, size.height)
                    )

                    onDrawWithContent {
                        drawIntoCanvas { canvas ->
                            // Save layer for blend mode
                            canvas.saveLayer(bounds = size.toRect(), paint = Paint())

                            // Draw the text (as content)
                            drawContent()

                            // Draw moving gradient only where text is
                            drawRect(
                                brush = gradientBrush,
                                blendMode = BlendMode.SrcIn
                            )

                            canvas.restore()
                        }
                    }
                }
        )
    }
}

@Composable
fun TextWithOutlineAndText(
    backgroundImage: Painter,
    text: String,
    outlineColor: Color,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        // Layer A - Background image
        Image(
            painter = backgroundImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Layer B - Outline text (bigger, nằm dưới)
        Text(
            text = text,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = outlineColor,
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(
                // Sử dụng shadow để tạo hiệu ứng outline
                shadow = Shadow(
                    color = outlineColor,
                    offset = Offset(0f, 0f),
                    blurRadius = 8f
                )
            )
        )

        // Layer C - Text chính (nằm trên)
        Text(
            text = text,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


















//// ảnh nền layer text

@Composable
fun AlphabetWithSharedAnimatedGradient(
    backgroundImage: Painter,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        0f, 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing)
        )
    )

    Box(modifier = modifier.fillMaxSize()) {
        // Layer A: Ảnh nền
        Image(
            painter = backgroundImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        val fullWidth = 600f // giả định chiều rộng gradient lớn (có thể lấy size thực tế trong onSizeChanged)
        val gradientBrush = Brush.linearGradient(
            colors = listOf(Color.Red, Color.Yellow, Color.Green, Color.Cyan, Color.Blue, Color.Magenta, Color.Red),
            start = Offset(x = fullWidth * progress - fullWidth, y = 0f),
            end = Offset(x = fullWidth * progress, y = 100f)
        )

        val alphabet = ('A'..'Z').toList()

        LazyVerticalGrid(
            columns = GridCells.Fixed(6),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(alphabet.size) { index ->
                LetterWithSharedBrush(
                    letter = alphabet[index],
                    brush = gradientBrush,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(60.dp)
                )
            }
        }
    }
}

@Composable
fun LetterWithSharedBrush(
    letter: Char,
    brush: Brush,
    modifier: Modifier = Modifier
) {
    Text(
        text = letter.toString(),
        fontSize = 64.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier.drawWithCache {
            onDrawWithContent {
                drawIntoCanvas { canvas ->
                    canvas.saveLayer(bounds = size.toRect(), paint = Paint())
                    drawContent()
                    drawRect(brush = brush, blendMode = BlendMode.SrcIn)
                    canvas.restore()
                }
            }
        }
    )
}









