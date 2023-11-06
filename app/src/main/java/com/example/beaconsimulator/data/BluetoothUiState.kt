package com.example.beaconsimulator.data

/** Esta data class almacena el estado de la UI a través de sus variables
 * bluetooth [bluetoothEnabled]
 * location [locationEnabled]
 */

data class BluetoothUiState(
    // si el bluetooth está activo en el dispositivo es true
    val bluetoothEnabled : Boolean = false,
    // si la ubicación está activa en el dispositivo es true
    val locationEnabled : Boolean = false,

    // notificacion para activar bluetooth
    val bluetoothEnabling : Boolean = false,
    //notificacion para activar location
    val locationEnabling : Boolean = false

)
