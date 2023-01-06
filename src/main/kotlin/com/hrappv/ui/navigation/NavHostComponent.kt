package com.hrappv.ui.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.hrappv.di.AppComponent
import com.arkivanov.decompose.ComponentContext
import com.hrappv.di.DaggerAppComponent
import com.hrappv.ui.feature.EmployeResult.EmployResultScreenComponent
import com.hrappv.ui.feature.main.MainScreenComponent
import com.arkivanov.decompose.*
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.hrappv.ui.feature.about.AboutComponent
import com.hrappv.ui.feature.add_employe.AddEmployeScreenComponent
import com.hrappv.ui.feature.home_screen.HomeComponent
import com.hrappv.ui.feature.main.MainScreen2
import com.hrappv.ui.feature.main.NavMenu
import com.hrappv.ui.feature.settings.SettingsComponent
import kotlinx.coroutines.launch

/**
 * All navigation decisions are made from here
 */
class NavHostComponent(
//    private val authenticated: UserAuthSate,
    private val componentContext: ComponentContext,
) : Component, ComponentContext by componentContext {

    /**
     * Available screensSelectApp
     */
//    private sealed class Config : Parcelable {
////        object Splash : Config()
//
//        //        object Login : Config()
//        object Home : Config()
//        object AddEmployee : Config()
//        object RegisterAttends : Config()
//        object Settings : Config()
//        object About : Config()
//        data class Main(val userAuthState: UserAuthSate) : Config()
//
//    }

    private val appComponent: AppComponent = DaggerAppComponent
        .create()

    /**
     * Router configuration
     */
//    private val router = router<Config, Component>(
//        initialConfiguration = Config.Home,
//        childFactory = ::createScreenComponent
//    )


    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        key = "Stack",
        initialConfiguration = Config.Home,
        childFactory = ::createScreenComponent
    )


    /**
     * When a new navigation request made, the screen will be created by this method.
     */
    private fun createScreenComponent(config: Config, componentContext: ComponentContext): Component {
        return when (config) {
//            is Config.Splash -> SplashScreenComponent(
//                appComponent = appComponent,
//                componentContext = componentContext,
//                onSplashFinished = ::onSplashFinished,
//            )

            is Config.Home -> HomeComponent(
                appComponent = appComponent,
                componentContext = componentContext,
//                userAuthState = config.userAuthState,
//                onClickAddEmployee = ::addEmployeScreenStart,
                onBackPress = ::onBackPress

            )

            is Config.AddEmployee -> AddEmployeScreenComponent(
                appComponent = appComponent,
                componentContext = componentContext,
//                userAuthState = config.userAuthState,
//                onClickAddEmployee = ::addEmployeScreenStart,
                onBackPress = ::onBackPress

            )

            is Config.RegisterAttends -> EmployResultScreenComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onBackPress = ::onBackPress
            )

            is Config.Settings -> SettingsComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onBackPress = ::onBackPress

            )

            is Config.About -> AboutComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onBackPress = ::onBackPress

            )

            is Config.Main -> MainScreenComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                userAuthState = config.userAuthState,
//                onClickAddEmployee = ::addEmployeScreenStart,
                onClickHome = ::startHomeScreen,
                onClickEmpResult = ::startRegisterAttendsScreen

            )


        }
    }


    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun render() {
        val coroutineScope = rememberCoroutineScope()

        var isMenuPressed by remember { mutableStateOf(false) }
        val childStack: Value<ChildStack<*, Component>> = stack
        val child = childStack.subscribeAsState()
        val activeComponent= child.value.active.instance


//        Children(
//            routerState = router.state,
//            animation = crossfadeScale()
//        ) { child ->
//            child.instance.render()
//        }
        MainScreen2(
//                modifier = Modifier
//                    .weight(0.15f),
            activeComponent = activeComponent,
            onNavIconClick = {
                coroutineScope.launch {
//                    scaffoldState.drawerState.open()
                    isMenuPressed = !isMenuPressed


                }
            },
            onHomeClick = { startHomeScreen() },
            onAddEmployeeClick = ::startAddEmployeeScreen,
            onEmployeeResultClick = ::startRegisterAttendsScreen,
            onSettingsClick = ::startSettingsScreen,
            onAboutClick = ::startAboutScreen,
        ){
            Children(
                stack = stack,
                animation = stackAnimation(), //fade() + scale()
            ) {
                it.instance.render()

            }
        }

    }

    /**
     * Invoked when splash finish data sync
     */
//    private fun onSplashFinished() {
//        router.replaceCurrent(Config.Main)
////        navigation.push(Config.Login)
//    }

//    private fun onUserAuthentcated(
//        userAuthState: UserAuthSate
//    ) {
////        router.replaceCurrent(Config.Main(userAuthState))
//        router.replaceCurrent(Config.Main())
////        navigation.push(Config.Main(userAuthState))
//    }

    private fun addEmployeScreenStart() {
        navigation.bringToFront(Config.AddEmployee)
//        navigation.push(Config.EmployeResult)
    }

    private fun startHomeScreen() {
//        router.replaceCurrent(Config.Home)
        navigation.bringToFront(Config.Home)
    }

    private fun startAddEmployeeScreen() {
//        router.replaceCurrent(Config.Home)
        navigation.bringToFront(Config.AddEmployee)
    }

    private fun startRegisterAttendsScreen() {
//        router.replaceCurrent(Config.EmployeResult)
        navigation.bringToFront(Config.RegisterAttends)
    }

    private fun startSettingsScreen() {
//        router.replaceCurrent(Config.EmployeResult)
        navigation.bringToFront(Config.Settings)
    }

    private fun startAboutScreen() {
//        router.replaceCurrent(Config.EmployeResult)
        navigation.bringToFront(Config.About)
    }

    private fun onBackPress() {
        navigation.pop()
//        router.replaceCurrent(Config.Main)

    }
}
//
//@Composable
//fun rememberNavController(
//    context: ApplicationContext,
//    startDestination: Screens,
//    backStackScreens: MutableSet<Screens> = mutableSetOf()
//): MutableState<NavController> = rememberSaveable {
//    mutableStateOf(NavController(context, startDestination, backStackScreens))
//}