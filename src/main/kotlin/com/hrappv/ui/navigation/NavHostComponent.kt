package com.hrappv.ui.navigation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.crossfadeScale
//import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
//import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
//import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
//import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
//import com.arkivanov.decompose.router.stack.pop
//import com.arkivanov.decompose.router.stack.push
//import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
//import com.arkivanov.decompose.router.stack.StackNavigation
//import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.statekeeper.Parcelable
//import com.arkivanov.essenty.parcelable.Parcelable
import com.hrappv.di.AppComponent
import com.arkivanov.decompose.ComponentContext
import com.hrappv.di.DaggerAppComponent
import com.hrappv.ui.feature.EmployeResult.EmployResultScreenComponent
import com.hrappv.ui.feature.main.MainScreenComponent
import com.hrappv.ui.feature.splash.SplashScreenComponent
import com.arkivanov.decompose.*
import com.hrappv.ui.feature.login.LoginComponent
import com.hrappv.ui.security.UserAuthSate

/**
 * All navigation decisions are made from here
 */
class NavHostComponent(
    private val componentContext: ComponentContext,
) : Component, ComponentContext by componentContext {

    /**
     * Available screensSelectApp
     */
    private sealed class Config : Parcelable {
        object Splash : Config()
        object Login : Config()
        data class Main(val userAuthState: UserAuthSate) : Config()
        object EmployeResult : Config()
    }

    private val appComponent: AppComponent = DaggerAppComponent
        .create()

    /**
     * Router configuration
     */
    private val router = router<Config, Component>(
        initialConfiguration = Config.Splash,
        childFactory = ::createScreenComponent
    )
//    private val navigation = StackNavigation<Config>()
//    private val stack = childStack(
//        source = navigation,
//        key = "Stack",
//        initialConfiguration = Config.Main(UserAuthSate()),
//        childFactory = ::createScreenComponent
//    )


    /**
     * When a new navigation request made, the screen will be created by this method.
     */
    private fun createScreenComponent(config: Config, componentContext: ComponentContext): Component {
        return when (config) {
            is Config.Splash -> SplashScreenComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onSplashFinished = ::onSplashFinished,
            )


            is Config.EmployeResult -> EmployResultScreenComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onBackClickEmpResult = ::employScreenStartBackPress
            )

            is Config.Main -> MainScreenComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                userAuthState = config.userAuthState,
                onClickEmpResult = ::employScreenStart

            )

            Config.Login -> LoginComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onUserAuthentcated = ::onUserAuthentcated,
            )


        }
    }

    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun render() {
        Children(

            routerState = router.state,
            animation = crossfadeScale()
        ) { child ->
            child.instance.render()
        }
//        Children(
//            stack = stack,
//            animation = stackAnimation(fade() + scale()),
//        ) {
//            it.instance.render()
//        }
    }

    /**
     * Invoked when splash finish data sync
     */
    private fun onSplashFinished() {
        router.replaceCurrent(Config.Login)
//        navigation.push(Config.Login)
    }

    private fun onUserAuthentcated(userAuthState: UserAuthSate) {
        router.replaceCurrent(Config.Main(userAuthState))
//        navigation.push(Config.Main(userAuthState))
    }


    private fun employScreenStart() {
        router.replaceCurrent(Config.EmployeResult)
//        navigation.push(Config.EmployeResult)
    }

    private fun employScreenStartBackPress() {
//        navigation.pop()
//        router.replaceCurrent(Config.Main)

    }
}