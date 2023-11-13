package com.example.beaconsimulator.ui

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BluetoothViewModel : ViewModel() {

    private var bluetoothAdapter: BluetoothAdapter? = null


    private val _isPermissionGranted = MutableLiveData<Boolean>()
    val isPermissionGranted: LiveData<Boolean> = _isPermissionGranted
    fun getBluetoothAdapter(context: Context): BluetoothAdapter?{
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        return bluetoothAdapter
    }

    fun onPermissionResult(permission: String, isGranted: Boolean){
        if(permission == Manifest.permission.ACCESS_COARSE_LOCATION){
            _isPermissionGranted.value = isGranted
        }
    }
}