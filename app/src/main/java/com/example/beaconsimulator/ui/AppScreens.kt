package com.example.beaconsimulator.ui

sealed class AppScreens(val route: String){

    object BluetoothScreen: AppScreens("bluetooth_screen")
    object DevicesScreen: AppScreens ("devices_screen")
}
