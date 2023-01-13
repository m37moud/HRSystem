package com.hrappv.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun NavigationMenuItem(
    selected: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector,
    label: String,
    isMenuPressed: Boolean = false,

    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    selectedContentColor: Color = MaterialTheme.colors.primary,
    unselectedContentColor: Color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
) {
    val ripple = rememberRipple(
        bounded = true,
        color = selectedContentColor
    )
        Row(
            modifier = modifier
                .selectable(
                    selected = selected,
                    onClick = onClick,
                    enabled = enabled,
                    role = Role.Button,
                    interactionSource = interactionSource,
                    indication = ripple
                ).padding(4.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
//        Spacer(modifier = modifier.width(8.dp))
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (selected) selectedContentColor else unselectedContentColor,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            if (isMenuPressed) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = if (selected) selectedContentColor else unselectedContentColor
                )

                Spacer(modifier = Modifier.weight(1f, false))
//            Spacer(modifier = modifier.width(150.dp))


            }
        }

}