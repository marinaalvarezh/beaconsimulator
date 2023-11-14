package com.example.beaconsimulator.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PermissionDialog(
    textPermission: TextDialogPermission,
    isPermanentlyDenied: Boolean,
    settingsButton:() -> Unit,
    onDismiss: () -> Unit,
    onOk: () -> Unit
){
    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
          Row(
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(10.dp),
              horizontalArrangement = Arrangement.End
          ) {
              if(isPermanentlyDenied){
                  Button(
                      onClick = {settingsButton()},
                      modifier = Modifier.padding(10.dp)
                  ){
                      Text(text = "Go to Settings")
                  }
              } else {
                  Button(
                      onClick = {onOk()},
                      modifier = Modifier.padding(10.dp)
                  ){
                      Text(text = "OK")
                  }
              }
          }
        },
        title = {
            Text(text = "Permission Required")
        },
        text = {
            Text(text = textPermission.getText(isPermanentlyDenied= isPermanentlyDenied))
        }
    )

}

interface TextDialogPermission{
    fun getText(isPermanentlyDenied: Boolean): String
}

class TextLocationCoarsePermission : TextDialogPermission{
    override fun getText(isPermanentlyDenied: Boolean): String {
        return if(isPermanentlyDenied){
            "You have decline Approximate Location Permission permanently. You can go to app settings to grant it"
        }else{
            "Access to your approximate location is required. We use your approximate location to enhance searches"
        }
    }
}

class TextLocationFinePermission : TextDialogPermission{
    override fun getText(isPermanentlyDenied: Boolean): String {
        return if(isPermanentlyDenied){
            "You have decline Precise Location Permission permanently. You can go to app settings to grant it"
        }else {
            "Precise location permission is needed. Your precise location allows us to provide better searches"
        }
    }
}

class TextNearDevicesPermission : TextDialogPermission{
    override fun getText(isPermanentlyDenied: Boolean): String {
        return if(isPermanentlyDenied){
            "You have decline the access to Nearby Devices permanently. You can go to app settings to grant it"
        }else {
            "Permission to access nearby devices is required. We need to access nearby devices to enhance connectivity"
        }
    }
}
