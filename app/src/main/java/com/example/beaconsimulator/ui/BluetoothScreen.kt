package com.example.beaconsimulator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.beaconsimulator.ui.AppScreens


@Composable
fun BluetoothScreen(
    navController: NavController
) {
    //fuente predeterminda para la main screen
    val mainscreenFamily = FontFamily(
        Font(R.font.roboto_thin, FontWeight.Thin),
        Font(R.font.anton_regular, FontWeight.Normal)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Beacon\nSimulator",
            modifier = Modifier
                .padding(30.dp),
            color = MaterialTheme.colors.onSurface,
            fontFamily = mainscreenFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 65.sp,
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(R.drawable.beacon_icon),
            contentDescription = "Icono de una baliza en la pantalla principal",
            modifier = Modifier
                .size(175.dp)
        )

        val btn = Button (
            onClick = {
                navController.navigate(AppScreens.DevicesScreen.route) {
                    popUpTo(AppScreens.BluetoothScreen.route)
                }
            },
            modifier = Modifier
                .padding(80.dp)
                .width(200.dp)
                .height(75.dp)
                .clip(RoundedCornerShape(15.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary
            )

        ){
            Text(
                text = "Search",
                fontSize = 40.sp,
                fontFamily = mainscreenFamily,
                fontWeight = FontWeight.Thin
            )
        }
    }
}

