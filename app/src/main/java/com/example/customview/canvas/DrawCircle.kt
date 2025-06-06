package com.example.customview.canvas

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp


@Composable
fun DrawCircleSample(modifier: Modifier)
{

    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing)
        )
    )


    var b = Brush.sweepGradient(listOf<Color>(
        Color.Red,
        Color.Yellow,
        Color.Blue,
        Color.Black
    ))


    Column {
        Canvas(Modifier.height(300.dp).fillMaxWidth()) {
            val brushRect =  Brush.linearGradient(
                colors = listOf(Color.Red,Color.Yellow, Color.Magenta, Color.Blue),
                start = Offset.Zero,
                end = Offset(size.width, size.height)
            )

            val brushRect2 = Brush.sweepGradient(listOf<Color>(
                Color.Red,
                Color.Yellow,
                Color.Blue,
                Color.Black
            ))

            val brushRect3 = Brush.radialGradient(listOf<Color>(
                Color.Red,
                Color.Yellow,
                Color.Blue,
                Color.Black
            ))

            rotate(degrees = angle, pivot = Offset(size.width/2,size.height/2)) {
                drawRect(
                    brush = brushRect2,
                    topLeft = Offset((size.width - (size.width/2))/2, size.height/2),
                    size = Size(size.width/2, size.height),
                    style = Stroke(width = 50f)
                )
            }

        }

        Spacer(modifier = Modifier.height(50.dp))

        Canvas(Modifier.height(800.dp).fillMaxWidth().rotate(angle)) {
            drawCircle(b,size.width/3, Offset(size.width/2,size.height/2))
        }
    }

}