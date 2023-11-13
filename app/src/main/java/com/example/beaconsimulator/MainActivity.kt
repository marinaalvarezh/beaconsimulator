package com.example.beaconsimulator

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.beaconsimulator.ui.BluetoothViewModel
import com.example.beaconsimulator.ui.Navigation
import com.example.beaconsimulator.ui.theme.BeaconSimulatorTheme


class MainActivity : ComponentActivity() {

    private lateinit var bluetoothStateReceiver: BroadcastReceiver
    private val bluetoothViewModel: BluetoothViewModel by viewModels()

    private var requestPermissionLocationLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
                isGranted->
            if(isGranted){
                Toast.makeText(this, "Permission Location Granted", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Permission Location Denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeaconSimulatorTheme {
                Navigation(bluetoothViewModel,onBluetoothStateChanged = {
                    notificationEnableBluetooth()
                })
            }
        }
    }
    override fun onStart(){
        super.onStart()
        notificationEnableBluetooth()
        registerChangesBluetooth()
        requestPermissionLocationLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }
    override fun onResume(){
        super.onResume()
        val filter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(bluetoothStateReceiver, filter)
    }
    override fun onPause(){
        super.onPause()
        unregisterReceiver(bluetoothStateReceiver)
    }

    //fun que hace aparecer la ventana para aceptar/denegar activar el Bluetooth
    private fun notificationEnableBluetooth(){
        val bluetoothAdapter = bluetoothViewModel.getBluetoothAdapter(this)
        if(bluetoothAdapter != null){
            if(bluetoothAdapter?.isEnabled == false){
                //lanza ventana activar bluetooth permitir/denegar
                    val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startBluetoothIntent.launch(enableBluetoothIntent)
            }
            else{
                Toast.makeText(this, "Bluetooth is enable", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this, "This device does not support Bluetooth", Toast.LENGTH_SHORT).show()
        }
    }

    private val startBluetoothIntent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            notificationEnableBluetooth()
        }

    //funcion que registra los cambios en el estado del Bluetooth
    private fun registerChangesBluetooth(){
        bluetoothStateReceiver = object : BroadcastReceiver (){
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                if (BluetoothAdapter.ACTION_STATE_CHANGED == action){
                    val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                    if (state == BluetoothAdapter.STATE_OFF || state == BluetoothAdapter.STATE_TURNING_OFF){
                        notificationEnableBluetooth()
                    }
                }
            }
        }
    }

}

