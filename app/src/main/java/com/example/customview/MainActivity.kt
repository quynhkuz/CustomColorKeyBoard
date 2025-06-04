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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.customview.ui.theme.CustomviewTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            CustomviewTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }


//            ColorWheelExample()
//            Spacer(modifier = Modifier.height(50.dp))
//            AdvancedExample()

//            RunningSquareExample()

//            KeyboardDemo()


//            KeyboardWithMovingBackgroundText222()

//            TextWithMaskedColor(
//                backgroundImage = painterResource(id = R.drawable.splash),
//                text = "HELLO",
//                maskColor = Color.Red,
//                modifier = Modifier
//                    .fillMaxSize()
//            )


//            AnimatedGradientTextMask(
//                backgroundImage = painterResource(id = R.drawable.splash),
//                text = "JETPACK",
//                modifier = Modifier.fillMaxSize()
//            )


//            LedRunningAlphabet(
//                backgroundImage = painterResource(id = R.drawable.splash),
//                modifier = Modifier.fillMaxSize()
//            )



//            TextWithOutlineAndText(
//                backgroundImage = painterResource(id = R.drawable.splash),
//                text = "HELLO",
//                outlineColor = Color.Red,
//                textColor = Color.Yellow,
//                modifier = Modifier
//                    .fillMaxSize()
//            )


            HomeScreen()


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
//        Greeting("Android")
//        ColorWheelExample()
//        Spacer(modifier = Modifier.height(50.dp))
//        AdvancedExample()
//
//        RunningSquareExample()

//        KeyboardDemo()

//        KeyboardWithMovingBackgroundText222()

    }
}