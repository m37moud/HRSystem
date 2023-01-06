package com.hrappv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.NavigationRail
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hrappv.ui.navigation.Config

@Composable
fun SideBarMenu(modifier: Modifier = Modifier,
//                mainViewModel: MainViewModel
) {
    val screens = listOf(
        Config.Home,
        Config.AddEmployee,
//        Screens.RegisterAttends,
//        Screens.Settings,
//        Screens.About,
//        Screens.LogOut,
//        Screens.NewWindow,
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