package com.example.customview


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout


@Composable
fun AdsView()
{
    ConstraintLayout(modifier = Modifier.background(Color.Red).fillMaxSize()) {

        val (center, circle1, circle2, circle3) = createRefs()

        Image(painter = painterResource(R.drawable.splash),"", modifier = Modifier.constrainAs(center) {})

        Image(painter = painterResource(R.drawable.ic_launcher_foreground),"", modifier = Modifier.constrainAs(circle1) {
            top.linkTo(center.bottom, margin = 15.dp)
            start.linkTo(center.start)
            end.linkTo(center.end)
            bottom.linkTo(parent.bottom)
        })


//        Canvas(modifier = Modifier.size(100.dp)) {
//            drawIntoCanvas { canvas ->
//            }
//        }

    }
}

