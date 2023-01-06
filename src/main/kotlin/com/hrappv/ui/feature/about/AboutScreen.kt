package com.hrappv.ui.feature.about

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card( Modifier.fillMaxSize().padding(120.dp),
            backgroundColor = MaterialTheme.colors.onPrimary,
            elevation = 10.dp,
        ) {
            Column(
                modifier = Modifier.weight(0.9f).padding(120.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = modifier.height(16.dp))

                Text(
                    "Human Resources System", color = MaterialTheme.colors.onSecondary,
                    style = MaterialTheme.typography.h3
                )
                Spacer(modifier = modifier.height(16.dp))
                Text(
                    "Powered by Mahmoud Aly", color = MaterialTheme.colors.onSecondary,
                    style = MaterialTheme.typography.h4
                )
                Spacer(modifier = modifier.height(16.dp))
                Text(
                    "TEL : 01148588723", color = MaterialTheme.colors.onSecondary,
                    style = MaterialTheme.typography.h5
                )
            }

        }
    }

}