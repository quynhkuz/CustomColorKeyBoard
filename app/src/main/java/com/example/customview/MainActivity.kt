package com.example.customview

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.customview.canvas.DrawCircleSample
import com.example.customview.ui.theme.CustomviewTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeScreen()

//            DrawCircleSample(modifier = Modifier.fillMaxSize().padding(top = 100.dp))

        }
    }
}

@Composable
fun HomeScreen()
{
    Box(modifier = Modifier.fillMaxSize())
    {

        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        KeyboardWithMovingGradient()

    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CustomviewTheme {

        DrawCircleSample(modifier = Modifier.fillMaxSize())

    }
}