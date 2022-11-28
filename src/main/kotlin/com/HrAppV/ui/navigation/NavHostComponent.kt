package com.HrAppV.ui.navigation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.crossfadeScale
import com.arkivanov.decompose.statekeeper.Parcelable
import com.HrAppV.di.AppComponent
import com.HrAppV.di.DaggerAppComponent
import com.HrAppV.ui.feature.EmployeResult.EmployResultScreenComponent
import com.HrAppV.ui.feature.main.MainScreenComponent
import com.HrAppV.ui.feature.splash.SplashScreenComponent
import com.arkivanov.decompose.*

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
        object Main : Config()
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
               onBackClickEmpResult = :: employScreenStartBackPress
            )

            Config.Main -> MainScreenComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onClickEmpResult = ::employScreenStart

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
    }

    /**
     * Invoked when splash finish data sync
     */
    private fun onSplashFinished() {
        router.replaceCurrent(Config.Main)
    }


    private fun employScreenStart() {
        router.replaceCurrent(Config.EmployeResult)
    }
    private fun employScreenStartBackPress() {
//        router.pop()
        router.replaceCurrent(Config.Main)

    }
}