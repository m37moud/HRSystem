package com.hrappv.ui.feature.add_department

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.*
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.hrappv.di.AppComponent
import com.hrappv.di.DaggerAppComponent
import com.hrappv.ui.feature.EmployeResult.EmployResultScreenComponent
import com.hrappv.ui.feature.about.AboutComponent
import com.hrappv.ui.feature.add_employe.AddEmployeScreenComponent
import com.hrappv.ui.feature.home_screen.HomeComponent
import com.hrappv.ui.feature.main.MainScreenComponent
import com.hrappv.ui.feature.settings.SettingsComponent
import com.hrappv.ui.feature.view_employees.ViewEmployeesComponent
import com.hrappv.ui.navigation.Component
import com.hrappv.ui.navigation.Config
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DefaultDepartmentComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onBackPress: () -> Unit
) : Component, ComponentContext by componentContext {

    private val _isBackPressed = MutableStateFlow(false)
    val backToMain: StateFlow<Boolean> = _isBackPressed

//    init {
//        appComponent.inject(this)
//    }

    private val appComponent: AppComponent = DaggerAppComponent
        .create()

    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        key = "defaultDepartment",
        initialConfiguration = Config.Department,
        childFactory = ::createScreenComponent
    )


    private fun createScreenComponent(config: Config, componentContext: ComponentContext): Component {
        return when (config) {

            is Config.Department -> DepartmentComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onAddDepartment = ::startAddDepartmentScreen,
                onBackPress = ::onBackPress

            )

            is Config.AddDepartment -> AddDepartmentComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onBackPress = ::onBackPress

            )


        }
    }


    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun render() {


        Children(
            stack = stack,
//            animation = stackAnimation(), //fade() + scale()
            animation =  stackAnimation { _, _, direction ->
                if (direction.isFront) {
                    slide() + fade()
                } else {
                    scale(frontFactor = 1F, backFactor = 0.7F) + fade()
                }
            },
        ) {
            it.instance.render()

        }
    }


    private fun startAddDepartmentScreen() {
//        router.replaceCurrent(Config.Home)
        if (stack.value.active.configuration !is Config.AddDepartment) {

            navigation.replaceCurrent(Config.AddDepartment)
        }
    }

    private fun onBackPress() {
        navigation.replaceCurrent(Config.Department)

//        navigation.pop()
//        router.replaceCurrent(Config.Main)

    }

    @Parcelize
    private sealed class Config(
//        val index: Int,
//        val isBackEnabled: Boolean,
    ) : Parcelable {
        object Department : Config()

        object AddDepartment : Config()
    }
}

