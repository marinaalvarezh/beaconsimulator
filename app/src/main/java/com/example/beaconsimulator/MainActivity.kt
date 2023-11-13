package com.example.beaconsimulator

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beaconsimulator.ui.*
import com.example.beaconsimulator.ui.theme.BeaconSimulatorTheme


class MainActivity : ComponentActivity() {

    private lateinit var bluetoothStateReceiver: BroadcastReceiver

    private val permissionsToRequest = arrayOf(
        //ubicacion aproximada
        Manifest.permission.ACCESS_COARSE_LOCATION,
        //ubicacion precisa
        Manifest.permission.ACCESS_FINE_LOCATION,
        //dispositivos cercanos
        Manifest.permission.NEARBY_WIFI_DEVICES
    )


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeaconSimulatorTheme {

                val bluetoothViewModel = viewModel<BluetoothViewModel>()
                val secondPermissionDialog = bluetoothViewModel.secondPermissionDialogList

                /**inicializa el Activity Result Launcher, contract = tipo de accion, onResult = Callback*/
                val multiplePermissionsResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { perms ->
                        /**perms : Map <String,Boolean?> la clave es el permiso y el valor boolean asociado accept/deny*/
                        permissionsToRequest.forEach {permission ->
                            bluetoothViewModel.onPermissionResult(
                                permission = permission,
                                isGranted = perms[permission] ?: false
                            )
                        }

                    }
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Button(onClick = {
                        multiplePermissionsResultLauncher.launch(permissionsToRequest)
                    }) {
                        Text(text = "Accept permissions")
                    }
                }

                secondPermissionDialog
                    .reversed()
                    .forEach {permission ->
                    PermissionDialog(
                        textPermission = when (permission) {
                            Manifest.permission.ACCESS_COARSE_LOCATION -> {
                                TextLocationCoarsePermission()
                            }
                            Manifest.permission.ACCESS_FINE_LOCATION -> {
                                TextLocationFinePermission()
                            }
                            Manifest.permission.NEARBY_WIFI_DEVICES -> {
                                TextNearDevicesPermission()
                            }
                        else -> return@forEach
                        },
                        isPermanentlyDenied = !shouldShowRequestPermissionRationale(permission),
                        settingsButton = ::openSettings,
                        onDismiss = { bluetoothViewModel.dismissDialog()},
                        onOk = {
                            bluetoothViewModel.dismissDialog()
                            multiplePermissionsResultLauncher.launch(arrayOf(permission))
                        }
                    )
                }


            }
        }
    }
 /**   override fun onStart(){
        super.onStart()
        //notificationEnableBluetooth()
        //registerChangesBluetooth()
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
*/


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

fun Activity.openSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}
