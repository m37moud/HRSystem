package com.hrappv.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
 fun NavItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    name: String,
    isMenuPressed: Boolean,
    selected: Boolean = false,
    enabled: Boolean = true,


    click: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .clickable { click() },
        verticalAlignment = Alignment.CenterVertically

    ) {
        Spacer(modifier = modifier.width(8.dp))
        Image(
            imageVector = icon,
            modifier = Modifier.size(25.dp),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
        )
        Spacer(modifier = modifier.width(8.dp))

        if (isMenuPressed) {
            Text(
                name, style = MaterialTheme.typography.subtitle2,
            )
            Spacer(modifier = modifier.weight(1f, false))
        }


    }

}

