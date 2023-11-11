package com.example.beaconsimulator.ui

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.lifecycle.ViewModel

class BluetoothViewModel : ViewModel() {

    private var bluetoothAdapter: BluetoothAdapter? = null
    fun getBluetoothAdapter(context: Context): BluetoothAdapter?{
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        return bluetoothAdapter
    }
}