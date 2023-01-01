package com.hrappv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.NavigationRail
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrappv.ui.navigation.Config

@Composable
fun SideBarMenu(modifier: Modifier = Modifier,
//                navController: NavController
) {
    val screens = listOf(
        Config.Main,
        Config.AddEmployee,
        Config.RegisterAttends,
        Config.Settings,
        Config.About,
        Config.LogOut,
//        Config.NewWindow,
    )
//    val currentScreen by remember {
//        navController.currentScreen
//    }
    NavigationRail(
        modifier = modifier
            .fillMaxHeight()
    ) {
        AppMenuHeader()
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth()
                .background(color = Color.DarkGray)
        )
        screens.forEach {
            NavigationMenuItem(
                modifier = Modifier.fillMaxWidth(),
//                selected = currentScreen == it,
                selected = false,
                icon = it.icon!!,
                label = it.label,
                onClick = {
//                    if (it is Screens.NewWindow) {
//                        navController.window(it)
//                    } else {
//                        navController.navigate(it)
//                    }
                },
            )
        }
    }
}