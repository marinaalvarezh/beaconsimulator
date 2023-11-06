package com.example.beaconsimulator

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.beaconsimulator.ui.theme.BeaconsimulatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeaconsimulatorTheme {
                BluetoothApp()
            }
        }
    }

    override fun onStart(){
        super.onStart()
        notificationEnableBluetooth()
    }

    // val bluetoothAdapter sera null si el dispositivo no tiene Bluetooth o no se puede acceder a él
    private val bluetoothAdapter: BluetoothAdapter? by lazy() {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    //fun que hace aparecer la ventana para aceptar/denegar activar el Bluetooth
    private fun notificationEnableBluetooth(){
        if(bluetoothAdapter != null){
            if(bluetoothAdapter?.isEnabled == false){
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
            result ->
            if(result.resultCode != Activity.RESULT_OK){
                notificationEnableBluetooth()
            }
        }
}

