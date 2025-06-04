package com.example.customview

import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Typeface
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.withSave
import androidx.compose.ui.graphics.withSaveLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.res.painterResource


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MaskedTextWindowEffect() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Layer 1: Ảnh nền
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Layer 2 + 3: Gradient + Cắt lỗ chữ cái
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    // Đảm bảo blend chỉ xảy ra trong khung này
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent {
                    // Layer 2: gradient động
                    val gradient = Brush.linearGradient(
                        colors = listOf(Color.Red, Color.Blue, Color.Yellow),
                        start = Offset.Zero,
                        end = Offset(size.width, size.height)
                    )

                    drawRect(brush = gradient)

                    // Layer 3: "xóa" chữ cái bằng BlendMode.Clear
                    drawIntoCanvas { canvas ->
                        val paint = Paint().asFrameworkPaint().apply {
                            isAntiAlias = true
                            textSize = size.minDimension / 2
                            textAlign = android.graphics.Paint.Align.CENTER
                            typeface = Typeface.DEFAULT_BOLD
                        }

                        val text = "A"
                        val x = size.width / 2
                        val y = size.height / 2 - (paint.descent() + paint.ascent()) / 2

                        // BlendMode.Clear để tạo "lỗ"
                        paint.blendMode = android.graphics.BlendMode.CLEAR
                        canvas.nativeCanvas.drawText(text, x, y, paint)
                        canvas.nativeCanvas.drawText("B", x, y, paint)
                    }
                }
        )
    }
}



@Composable
fun GradientInTextMask() {
    val infiniteTransition = rememberInfiniteTransition(label = "gradient")

    val offsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // 1️⃣ Ảnh nền (hiển thị ở mọi nơi TRỪ chữ A)
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 2️⃣ Gradient chỉ hiển thị BÊN TRONG chữ A
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawIntoCanvas { canvas ->
                val frameworkCanvas = canvas.nativeCanvas

                // Tạo một layer riêng để áp dụng mask
                val checkpoint = frameworkCanvas.saveLayer(
                    0f, 0f, size.width, size.height, null
                )

                // Vẽ chữ A như một "lỗ cắt" (DST_OUT để giữ lại phần bên ngoài chữ)
                val textPaint = android.graphics.Paint().apply {
                    isAntiAlias = true
                    textSize = size.minDimension / 1.5f
                    textAlign = android.graphics.Paint.Align.CENTER
                    typeface = Typeface.DEFAULT_BOLD
                    color = android.graphics.Color.BLACK
                    xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
                }

                // Vẽ gradient (sẽ chỉ hiển thị trong vùng chữ A)
                val gradientPaint = android.graphics.Paint().apply {
                    isAntiAlias = true
                    shader = android.graphics.LinearGradient(
                        offsetX, 0f,
                        offsetX + size.width / 2, size.height,
                        intArrayOf(
                            android.graphics.Color.RED,
                            android.graphics.Color.YELLOW,
                            android.graphics.Color.CYAN
                        ),
                        null,
                        android.graphics.Shader.TileMode.CLAMP
                    )
                }

                // Vẽ gradient toàn màn hình (nhưng chỉ hiển thị trong chữ A)
                frameworkCanvas.drawRect(0f, 0f, size.width, size.height, gradientPaint)

                // Vẽ chữ A (đóng vai trò mask)
                val text = "A"
                val x = size.width / 2
                val y = size.height / 2 - (textPaint.descent() + textPaint.ascent()) / 2
                frameworkCanvas.drawText(text, x, y, textPaint)

                // Kết thúc layer để áp dụng mask
                frameworkCanvas.restoreToCount(checkpoint)
            }
        }
    }
}




