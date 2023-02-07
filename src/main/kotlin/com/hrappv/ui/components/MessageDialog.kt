package com.hrappv.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun ShowMessageDialog(message: String, error : Boolean = false, onDismissRequest: () -> Unit, ok: () -> Unit, cancel: () -> Unit) {
    AlertDialog(
        modifier = Modifier.width(300.dp),
        onDismissRequest = onDismissRequest,
        title = { Text(text = if(error)"Error Message" else "Message",color = Color.Red,
//            modifier = Modifier.fillMaxSize()  ,
            maxLines = 1
        )
                },
        text = { Text(message , color = Color.Red, maxLines = 1,
//            modifier = Modifier.fillMaxWidth(),
        )
               },
        confirmButton = {
            Button(onClick = {
                ok()
                onDismissRequest()
            }) { Text("ok") }
        },
        dismissButton ={
            if(!error) {
                Button(onClick = {
                    cancel()
                    onDismissRequest()
                }) { Text("cancel") }
            }
        },


    )
}

