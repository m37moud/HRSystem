package com.hrappv.ui.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
//import androidx.compose.ui.platform.LocalConfiguration
import com.arkivanov.decompose.extensions.compose.jetbrains.rememberRootComponent
import com.hrappv.App
import com.hrappv.ui.components.AppWindowTitleBar
import com.hrappv.ui.feature.login.LoginScreen
import com.hrappv.ui.feature.login.LoginViewModel
import com.hrappv.ui.navigation.NavHostComponent
import com.hrappv.ui.value.AppThemeState
import com.hrappv.ui.value.HrAppVTheme
import com.hrappv.ui.value.R
import com.hrappv.ui.value.rememberAppThemeState
import com.theapache64.cyclone.core.Activity
import com.theapache64.cyclone.core.Intent
import javax.inject.Inject
import androidx.compose.ui.window.application as setContent

/**
 * The activity who will be hosting all screens in this app
 */
class MainActivity : Activity() {
    companion object {
        fun getStartIntent(): Intent {
            return Intent(MainActivity::class).apply {
                // data goes here
            }
        }
    }
    @Inject
    lateinit var viewModel: LoginViewModel

    override fun onCreate() {
        super.onCreate()

//        val configuration = LocalConfiguration.current
//        val screenHeight = configuration.screenHeightDp.dp
//        val screenWidth = configuration.screenWidthDp.dp
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel) {
            viewModel.init(scope)
        }

        val auth by viewModel.userAuthenticated.collectAsState(false)
        val themeState = rememberAppThemeState()
        setContent {
            Window(
                onCloseRequest = ::exitApplication,
                title = "${App.appArgs.appName} (${App.appArgs.version})",
                icon = painterResource("drawables/launcher_icons/system.png"),
                state = rememberWindowState(placement = WindowPlacement.Maximized,
                    width = 1024.dp, height = 600.dp),
//                state = rememberWindowState(width = screenWidth, height = screenHeight),
            ) {
                HrAppVTheme(isDark = themeState.isDarkTheme) {
                    if (auth) {
                        AppMainWindow(themeState)
                    } else {
                        AppLoginWindow(viewModel)
                    }


                    // Igniting navigation
                    rememberRootComponent(factory = ::NavHostComponent)
                        .render()
                }
            }

        }

    }
    @Composable //ApplicationScope.
    fun ApplicationScope.AppLoginWindow(viewModel: LoginViewModel) {// parameter -> applicationContext: ApplicationContext
        val loginWindowState = rememberWindowState(
            position = WindowPosition(Alignment.Center),
            width = 400.dp,
            height = 600.dp,
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
            CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Ltr
            ) {
                LoginScreen(viewModel)
            }
        }
    }

    @Composable
    private fun ApplicationScope.AppMainWindow(

        themeState: AppThemeState
    ) {
        val globalWindowState = rememberWindowState(
            position = WindowPosition(Alignment.Center),
            width = 1400.dp,
            height = 800.dp,
        )
        Window(
            onCloseRequest = {
//                SpringApplication.exit(applicationContext)
                exitApplication()
            },
            state = globalWindowState,
            undecorated = true,
            icon = painterResource("app-icon.svg")
        ) {
            CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Ltr
            ) {
                Surface(
                    modifier = Modifier.background(color = MaterialTheme.colors.background)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        AppWindowTitleBar(
//                            applicationContext.viewModel(),
                            globalWindowState,
                            themeState
                        ) {
                            exitApplication()
                        }
                        AppMainContainer()
                    }
                }
            }
        }
    }


    @Composable
    fun AppMainContainer() {
        // Igniting navigation
        rememberRootComponent(factory = ::NavHostComponent)
            .render()
    }


}
