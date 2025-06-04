package com.example.customview

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp


@Composable
fun DrawLedText() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.White)
            .drawWithContent {
                // 1. Vẽ nền đỏ trước
                drawRect(Color.Red)

                // 2. Vẽ nội dung gốc (ví dụ: Text, Image, v.v.)
                drawContent()

                // 3. Vẽ đường viền xanh sau
                drawRect(Color.Green, style = Stroke(width = 8f))
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Hello")
            Text("Any Body Here")
        }

    }


}