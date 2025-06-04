package com.example.customview

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun KeyboardWithMovingGradient() {
    val keyRows = listOf(
        listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
        listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
        listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
        listOf("Z", "X", "C", "V", "B", "N", "M")
    )

//    val canvasWidth = 900.dp
//    val canvasHeight = 900.dp

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
            textSize = 100f
            style = android.graphics.Paint.Style.FILL
            textAlign = android.graphics.Paint.Align.LEFT
            typeface = android.graphics.Typeface.DEFAULT_BOLD
        }

        val rowHeight = size.width/5
        val charSpacing = size.width/10

        drawIntoCanvas { canvas ->
            val frameworkCanvas = canvas.nativeCanvas

            val layer = frameworkCanvas.saveLayer(null, null)

            // Vẽ chữ trắng làm mask
            keyRows.forEachIndexed { rowIndex, row ->
                val offsetX = when (rowIndex) {
                    0 -> 0f
                    1 -> 0f
                    2 -> 25f
                    3 -> 50f
                    else -> 0f
                }
                val y = (rowIndex + 1) * rowHeight

                row.forEachIndexed { index, letter ->
                    val x = offsetX + index * charSpacing
                    paint.color = android.graphics.Color.WHITE
                    frameworkCanvas.drawText(letter, x, y, paint)
                }
            }
            val radius = size.minDimension / 2f
            val centerX = size.width / 2f
            val centerY = size.height / 2f

            val radians = Math.toRadians(angle.toDouble())
            val endX = centerX + radius * cos(radians).toFloat()
            val endY = centerY + radius * sin(radians).toFloat()

            val paintGradient = android.graphics.Paint().apply {
                isAntiAlias = true
                shader = android.graphics.LinearGradient(
                    centerX, centerY,  // start: tâm canvas
                    endX, endY,        // end: quay theo vòng tròn
                    intArrayOf(
                        android.graphics.Color.RED,
                        android.graphics.Color.MAGENTA,
                        android.graphics.Color.CYAN,
                        android.graphics.Color.YELLOW
                    ),
                    null,
                    android.graphics.Shader.TileMode.MIRROR
                )
                xfermode = android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN)
            }

            // Vẽ gradient phủ toàn bộ canvas, nhưng chỉ hiển thị trong vùng chữ do SRC_IN
            frameworkCanvas.drawRect(0f, 0f, size.width, size.height, paintGradient)

            frameworkCanvas.restoreToCount(layer)
        }
    }
}






