package com.hrappv.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun ShowMessageDialog(message: String, onDismissRequest: () -> Unit) {
    AlertDialog(
        modifier = Modifier.width(200.dp),
        onDismissRequest = onDismissRequest,
        buttons = { Button(onClick = { onDismissRequest() }) { Text("ok") } },
        title = { Text("Message", modifier = Modifier.fillMaxSize(), maxLines = 1) },
        text = { Text(message) })
}

