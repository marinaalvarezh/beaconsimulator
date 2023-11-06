package com.example.beaconsimulator.ui

import androidx.lifecycle.ViewModel
import com.example.beaconsimulator.data.BluetoothUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BluetoothViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BluetoothUiState())
    val uiState: StateFlow<BluetoothUiState> = _uiState.asStateFlow()

    fun enableBluetooth() {
        //show notification to enable Bluetooth

        _uiState.value = BluetoothUiState(bluetoothEnabling = true)
    }

    fun setBluetoothEnabled (enabled : Boolean){
        //update the data Bluetooth

        _uiState.value = BluetoothUiState(bluetoothEnabled = enabled)
    }
}