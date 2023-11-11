package com.example.beaconsimulator.ui

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DevicesScreen(
    navController : NavController
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "beaconsimulator2")
                },

                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(AppScreens.BluetoothScreen.route)
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground
            )
        },
        content = {
            Text(text = "adios")
        }
    )



}