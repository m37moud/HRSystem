package com.hrappv.ui.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.hrappv.ui.value.R

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
) {

    Box(
        modifier = Modifier.size(200.dp,200.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource("drawables/logo.png"),
            modifier = Modifier.size(100.dp),
            contentDescription = "Logo"
        )
    }
}

@Composable
fun ApplicationScope.Splash(
    viewModel: SplashViewModel,
) {
    val loginWindowState = rememberWindowState(
        position = WindowPosition(Alignment.Center),
//        width = 400.dp,
//        height = 600.dp,
    )
    Window(
        onCloseRequest = {
//                SpringApplication.exit(applicationContext)
            exitApplication()
        },
        state = loginWindowState,
        resizable = false,
        title = R.string.LOGIN,
        icon = painterResource("drawables/logo.png")
    ) {
        Box(
            modifier = Modifier.size(200.dp, 200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource("drawables/logo.png"),
                modifier = Modifier.size(100.dp),
                contentDescription = "Logo"
            )
        }
    }

}