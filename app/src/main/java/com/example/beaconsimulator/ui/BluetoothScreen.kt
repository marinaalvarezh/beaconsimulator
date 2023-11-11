package com.example.beaconsimulator

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.media.Image
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import com.example.beaconsimulator.ui.AppScreens


@Composable
fun BluetoothScreen(
    navController: NavController
) {
    //fuente predeterminda para la main screen
    val mainscreenFamily = FontFamily(
        Font(R.font.roboto_bold, FontWeight.Bold),
        Font(R.font.roboto_regular, FontWeight.Normal),
        Font(R.font.roboto_thin, FontWeight.Thin),
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
            modifier = Modifier.padding(60.dp),
            color = MaterialTheme.colors.onSurface,
            fontFamily = mainscreenFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 60.sp,
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(R.drawable.beacon_icon),
            contentDescription = "Icono de una baliza en la pantalla principal",
            modifier = Modifier.size(225.dp),
            alignment = Alignment.Center
        )

        Button (
            onClick = {
                navController.navigate(AppScreens.DevicesScreen.route) {
                    popUpTo(AppScreens.BluetoothScreen.route)
                }
            },
            modifier = Modifier
                .padding(100.dp)
                .width(200.dp)
                .height(75.dp)
                .clip(RoundedCornerShape(15.dp))
            /**
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onSurface
            )
            */
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






