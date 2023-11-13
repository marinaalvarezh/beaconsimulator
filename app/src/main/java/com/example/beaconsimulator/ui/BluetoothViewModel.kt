package com.example.beaconsimulator.ui

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BluetoothViewModel : ViewModel() {

    val secondPermissionDialogList = mutableStateListOf<String>()


    private val _arePermissionsGranted = MutableLiveData<Boolean>()
    val arePermissionsGranted: LiveData<Boolean> = _arePermissionsGranted

    fun dismissDialog(){
        secondPermissionDialogList.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ){
        /** si es rechazado por primera vez va a lista de permisos que se muestran por segunda vez */
        if(!isGranted && !secondPermissionDialogList.contains(permission)){
            secondPermissionDialogList.add(permission)
        }

    }
}