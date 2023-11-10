package com.example.beaconsimulator

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.EXTRA_STATE
import android.bluetooth.BluetoothAdapter.STATE_TURNING_OFF
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import com.example.beaconsimulator.ui.Navigation
import com.example.beaconsimulator.ui.theme.BeaconsimulatorTheme

class MainActivity : ComponentActivity() {

    lateinit var bluetoothStateReceiver: BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeaconsimulatorTheme {
                Navigation(onBluetoothStateChanged = {
                    notificationEnableBluetooth()
                })
            }
        }
    }

    override fun onStart(){
        super.onStart()
        notificationEnableBluetooth()
        registerChangesBluetooth()
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

    // val bluetoothAdapter sera null si el dispositivo no tiene Bluetooth o no se puede acceder a él
    private val bluetoothAdapter: BluetoothAdapter? by lazy() {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    //fun que hace aparecer la ventana para aceptar/denegar activar el Bluetooth
    private fun notificationEnableBluetooth(){
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

    fun registerChangesBluetooth(){
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

