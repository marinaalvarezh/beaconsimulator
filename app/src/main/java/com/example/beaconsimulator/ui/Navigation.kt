package com.example.beaconsimulator.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.beaconsimulator.BluetoothScreen

@Composable
fun Navigation(
){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.DevicesScreen.route){
        //administración de rutas del NavHost

        composable(route = AppScreens.BluetoothScreen.route){
            BluetoothScreen(navController)
        }

        composable(route = AppScreens.DevicesScreen.route){
            DevicesScreen(navController)
        }
    }
}