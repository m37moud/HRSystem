package com.hrappv


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.arkivanov.decompose.extensions.compose.jetbrains.rememberRootComponent
import com.hrappv.model.AppArgs
import com.hrappv.ui.components.AppWindowTitleBar
import com.hrappv.ui.feature.MainActivity
import com.hrappv.ui.feature.login.LoginScreen
import com.hrappv.ui.feature.login.LoginViewModel
import com.hrappv.ui.navigation.NavHostComponent
import com.hrappv.ui.value.AppThemeState
import com.hrappv.ui.value.HrAppVTheme
import com.hrappv.ui.value.R
import com.hrappv.ui.value.rememberAppThemeState
import com.theapache64.cyclone.core.Application
import com.toxicbakery.logging.Arbor
import com.toxicbakery.logging.Seedling
import javax.inject.Inject


class App(
    appArgs: AppArgs,
) : Application() {

    companion object {
        lateinit var appArgs: AppArgs
    }

    init {
        App.appArgs = appArgs
    }

    override fun onCreate() {
        super.onCreate()
        Arbor.sow(Seedling())

        Arbor.d("Starting app...")

        val splashIntent = MainActivity.getStartIntent()
        startActivity(splashIntent)
    }
}

/**
 * The magic begins here
 */
fun main() {

    val appArgs = AppArgs(
        appName = "Hr App", // To show on title bar
        version = "v1.0.0", // To show on title inside brackets
        versionCode = 100 // To compare with latest version code (in case if you want to prompt update)
    )

    App(appArgs).onCreate()
}
//
//@Inject
//lateinit var viewModel: LoginViewModel
////    val windowState = WindowState()
//
//fun main() = application {
//    val auth by remember { mutableStateOf(false) }
//    val themeState = rememberAppThemeState()
//    val scope = rememberCoroutineScope()
//
//    HrAppVTheme(false) {
//        if (!auth) {
//            AppLoginWindow()
//
//        } else {
//            AppMainWindow(themeState)
//
//        }
//    }
//}
//@Composable //ApplicationScope.
//fun ApplicationScope.AppLoginWindow() {// parameter -> applicationContext: ApplicationContext
//    val loginWindowState = rememberWindowState(
//        position = WindowPosition(Alignment.Center),
//        width = 400.dp,
//        height = 600.dp,
//    )
//    Window(
//        onCloseRequest = {
////                SpringApplication.exit(applicationContext)
//            exitApplication()
//        },
//        state = loginWindowState,
//        resizable = false,
//        title = R.string.LOGIN,
//        icon = painterResource("drawables/logo.png")
//    ) {
//        LoginScreen(viewModel)
//
////            CompositionLocalProvider(
////                LocalLayoutDirection provides LayoutDirection.Ltr
////            ) {
////                LoginScreen(viewModel)
////            }
//    }
//}
//@Composable
//private fun ApplicationScope.AppMainWindow(
//
//    themeState: AppThemeState
//) {
//    val globalWindowState = rememberWindowState(
//        placement = WindowPlacement.Maximized,
//        position = WindowPosition(Alignment.Center),
//        width = 1024.dp,
//        height = 600.dp,
//    )
//    Window(
//        onCloseRequest = {
////                SpringApplication.exit(applicationContext)
//            exitApplication()
//        },
//        title = "${App.appArgs.appName} (${App.appArgs.version})",
//        state = globalWindowState,
//        undecorated = true,
//        icon = painterResource("drawables/launcher_icons/system.png")
//    ) {
////            CompositionLocalProvider(
////                LocalLayoutDirection provides LayoutDirection.Ltr
////            ) {
//        Surface(
//            modifier = Modifier.background(color = MaterialTheme.colors.background)
//        ) {
//            Column(modifier = Modifier.fillMaxSize()) {
//                AppWindowTitleBar(
////                            applicationContext.viewModel(),
//                    globalWindowState,
//                    themeState
//                ) {
//                    exitApplication()
//                }
//                AppMainContainer()
//            }
//        }
////            }
//    }
//}
//
//
//@Composable
//fun AppMainContainer() {
//    // Igniting navigation
//    rememberRootComponent(factory = ::NavHostComponent)
//        .render()
////        root.render()
//}
//
