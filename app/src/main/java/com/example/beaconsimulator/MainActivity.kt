package com.example.beaconsimulator

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
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

    val mainscreenFamily = FontFamily(
        Font(R.font.roboto_thin, FontWeight.Thin),
        Font(R.font.anton_regular, FontWeight.Normal)
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

                /**Define diseño IU con boton que comienza la peticion de permisos*/
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "Beacon\nSimulator",
                        modifier = Modifier
                            .padding(30.dp),
                        color = MaterialTheme.colors.onSurface,
                        fontFamily = mainscreenFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 65.sp,
                        textAlign = TextAlign.Center
                    )

                    Image(
                        painter = painterResource(R.drawable.beacon_icon),
                        contentDescription = "Icono de una baliza en la pantalla principal",
                        modifier = Modifier
                            .size(175.dp)
                    )

                    Button (
                        onClick = {
                            if(ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                && ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                && ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.NEARBY_WIFI_DEVICES) == PackageManager.PERMISSION_GRANTED
                            ){
                                Toast.makeText(this@MainActivity, "ALL IS OK", Toast.LENGTH_SHORT).show()
                            }else{
                                multiplePermissionsResultLauncher.launch(permissionsToRequest)
                            }
                        },
                        modifier = Modifier
                            .padding(80.dp)
                            .width(200.dp)
                            .height(75.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.secondary,
                            contentColor = MaterialTheme.colors.onSecondary
                        )

                    ){
                        Text(
                            text = "Start",
                            fontSize = 40.sp,
                            fontFamily = mainscreenFamily,
                            fontWeight = FontWeight.Thin
                        )
                    }
                }

                /**Lanza segundo dialogo de permisos con los que han sido rechazados*/

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
                        /**la funcion should.. devuelve true cuando ya se ha negado el permiso y se quiere añadir explicacion, falso --> permiso aceptado/no volver a mostrar*/
                        settingsButton = ::openSettings,
                        /**referencia a la funcion openSettings*/
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
