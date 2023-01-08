package com.hrappv.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrappv.App

@Composable
fun AppMenuHeader() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp)
    ) {
        Icon(
            contentDescription = null,
            modifier = Modifier.size(50.dp),
            painter = painterResource("drawables/app-icon.svg")
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
//            text = stringResource("global.app.name"),
            text = "${App.appArgs.appName}",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}
