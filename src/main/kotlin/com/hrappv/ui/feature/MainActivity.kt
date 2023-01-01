package com.hrappv.ui.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
//import androidx.compose.ui.platform.LocalConfiguration
//import com.arkivanov.decompose.extensions.compose.jetbrains.rememberRootComponent
//import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
//import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.rememberRootComponent
import com.hrappv.App
import com.hrappv.di.AppComponent
import com.hrappv.di.DaggerAppComponent
import com.hrappv.ui.components.AppWindowTitleBar
import com.hrappv.ui.components.SideBarMenu
//import com.hrappv.ui.feature.login.AppLoginWindow
import com.hrappv.ui.feature.login.LoginScreen
import com.hrappv.ui.feature.login.LoginViewModel
import com.hrappv.ui.navigation.NavHostComponent
import com.hrappv.ui.security.UserAuthSate
import com.hrappv.ui.value.AppThemeState
import com.hrappv.ui.value.HrAppVTheme
import com.hrappv.ui.value.R
import com.hrappv.ui.value.rememberAppThemeState
import com.theapache64.cyclone.core.Activity
import com.theapache64.cyclone.core.Intent
import java.util.*
import javax.inject.Inject
import javax.swing.SwingUtilities
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

//    @Inject
//    lateinit var loginViewModel: LoginViewModel

    private val appComponent: AppComponent = DaggerAppComponent
        .create()
//val app = DaggerAppComponent.

//    val windowState = WindowState()
//    val lifecycle = LifecycleRegistry()
////    val root = runOnMainThreadBlocking { NavHostComponent(DefaultComponentContext(lifecycle)) }
//    val root =  NavHostComponent(DefaultComponentContext(lifecycle))

    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate() { //decompose-desktop-example-master
        super.onCreate()

//        val configuration = LocalConfiguration.current
//        val screenHeight = configuration.screenHeightDp.dp
//        val screenWidth = configuration.screenWidthDp.dp
//

//        val LocalComponentContext: ProvidableCompositionLocal<ComponentContext> =
//            staticCompositionLocalOf { error("Root component context was not provided") }
//
//        @Composable
//        fun ProvideComponentContext(componentContext: ComponentContext, content: @Composable () -> Unit) {
//            CompositionLocalProvider(LocalComponentContext provides componentContext, content = content)
//        }



        setContent {
            var authState by remember { mutableStateOf(UserAuthSate()) }
            val themeState = rememberAppThemeState()

//
//            CompositionLocalProvider( LocalLayoutDirection provides LayoutDirection.Ltr,
//                LocalViewConfiguration provides LocalViewConfiguration.current ) {

            val loginViewModel = appComponent.getmodel()
            val scope = rememberCoroutineScope()

            LaunchedEffect(loginViewModel) {
                loginViewModel.init(scope)
            }
            val authenticated by loginViewModel.userAuthSate.collectAsState()
            authState = authenticated

                HrAppVTheme(themeState.isDarkTheme) {
                    if (!authState.auth) {
                        AppLoginWindow(loginViewModel)

                    } else {
                        AppMainWindow(themeState,authState)

                    }
                }
//            }


//            Window(
//                onCloseRequest = ::exitApplication,
//                title = "${App.appArgs.appName} (${App.appArgs.version})",
//                icon = painterResource("drawables/launcher_icons/system.png"),
//                state = rememberWindowState(
//                    placement = WindowPlacement.Maximized,
//                    width = 1024.dp, height = 600.dp
//                ),
////                state = rememberWindowState(width = screenWidth, height = screenHeight),
//            ) {
//
////
//
////            CompositionLocalProvider(
////                LocalLayoutDirection provides LayoutDirection.Ltr
////            ) {
////                HrAppVTheme(isDark = themeState.isDarkTheme) {
////                singleWindowApplication(
////                    state = windowState,
////                    title = "Decompose Desktop Example",
////                ) {
////                    LifecycleController(lifecycle, windowState)
//                HrAppVTheme(false) {
////                    if (auth) {
////                        AppMainWindow(themeState)
////                    } else {
////
////                    AppLoginWindow(viewModel)
////
////                    }
//
//
//                    // Igniting navigation
//                    rememberRootComponent(factory = ::NavHostComponent)
//                        .render()
////                        root.render()
//
//                }
////            }
////                }
//            }

        }

    }

    @Composable //ApplicationScope.
    fun ApplicationScope.AppLoginWindow(loginViewModel:LoginViewModel) {// parameter -> applicationContext: ApplicationContext
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
            Surface(
                modifier = Modifier.background(color = MaterialTheme.colors.background)
            ) {
                LoginScreen(loginViewModel)
            }
//            CompositionLocalProvider(
//                LocalLayoutDirection provides LayoutDirection.Ltr
//            ) {
//                LoginScreen(viewModel)
//            }
        }
    }

    @Composable
    private fun ApplicationScope.AppMainWindow(
        themeState: AppThemeState,
        userState: UserAuthSate
    ) {
        val globalWindowState = rememberWindowState(
            placement = WindowPlacement.Maximized,
            position = WindowPosition(Alignment.Center),
            width = 1024.dp,
            height = 600.dp,
        )
        Window(
            onCloseRequest = {
//                SpringApplication.exit(applicationContext)
                exitApplication()
            },
            title = "${App.appArgs.appName} (${App.appArgs.version})",
            state = globalWindowState,
            undecorated = true,
            icon = painterResource("drawables/launcher_icons/system.png")
        ) {
//            CompositionLocalProvider(
//                LocalLayoutDirection provides LayoutDirection.Ltr
//            ) {
            Surface(
                modifier = Modifier.background(color = MaterialTheme.colors.background)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    AppWindowTitleBar(
                        userState.username,
                        globalWindowState,
                        themeState
                    ) {
                        exitApplication()
                    }
                    AppMainContainer()
                }
            }
//            }
        }
    }


    @Composable
    fun AppMainContainer() {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            SideBarMenu(
                modifier = Modifier
                    .weight(0.15f),
//                navController
            )
            Box(
                modifier = Modifier.fillMaxHeight()
                    .weight(0.85f)
            ) {
                AppNavigationHost()
            }
        }
    }
        // Igniting navigation
//        private val navController = rememberRootComponent(factory = ::NavHostComponent)
//            .render()
//        root.render()
    }


@Composable
fun AppNavigationHost() {
    rememberRootComponent(factory = ::NavHostComponent)
        .render()
}


    private inline fun <T : Any> runOnMainThreadBlocking(crossinline block: () -> T): T {
        lateinit var result: T
        SwingUtilities.invokeAndWait { result = block() }
        return result
    }

