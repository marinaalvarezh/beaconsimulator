package com.example.beaconsimulator.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.compose.ui.res.stringResource

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
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        },
        content = {
            Text(text = "adios")
        }
    )
}