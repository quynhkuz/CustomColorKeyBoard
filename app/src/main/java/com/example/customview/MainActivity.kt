package com.example.customview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.customview.ui.theme.CustomviewTheme

class MainActivity : ComponentActivity() {
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


            KeyboardWithMovingBackgroundText222()
        }
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

        KeyboardWithMovingBackgroundText222()

    }
}