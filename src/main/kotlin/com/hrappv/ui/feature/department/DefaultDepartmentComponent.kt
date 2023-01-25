package com.hrappv.ui.feature.department

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.*
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.hrappv.di.AppComponent
import com.hrappv.di.DaggerAppComponent
import com.hrappv.ui.feature.department.add_department.AddDepartmentComponent
import com.hrappv.ui.feature.department.show_departments.DepartmentComponent
import com.hrappv.ui.navigation.Component

class DefaultDepartmentComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onBackPress: () -> Unit
) : Component, ComponentContext by componentContext {


//    init {
//        appComponent.inject(this)
//    }

    private val appComponent: AppComponent = DaggerAppComponent
        .create()

    private val departmentNavigation = StackNavigation<DepartmentConfig>()
    private val departmentStack = childStack(
        source = departmentNavigation,
        initialConfiguration = DepartmentConfig.Department,
        handleBackButton = true,
        key = "departmentStack",
        childFactory = ::createScreenComponent,

    )


    private fun createScreenComponent(config: DepartmentConfig, componentContext: ComponentContext): Component {
        return when (config) {

            is DepartmentConfig.Department -> DepartmentComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onAddDepartment = ::startAddDepartmentScreen,
                onBackPress = ::onBackPress

            )

            is DepartmentConfig.AddDepartment -> AddDepartmentComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onBackPress = ::onBackPress

            )


        }
    }


    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun render() {
        val childStack: Value<ChildStack<*, Component>> = departmentStack
        val child = childStack.subscribeAsState()
        val activeComponent= child.value.active.instance

        println(activeComponent.toString())

        Children(
            stack = departmentStack,
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
        if (departmentStack.value.active.configuration !is DepartmentConfig.AddDepartment) {

            departmentNavigation.push(DepartmentConfig.AddDepartment)
        }
    }

    private fun onBackPress() {
//        navigation.replaceCurrent(Config.Department)
        if (departmentStack.value.active.configuration !is DepartmentConfig.Department) {

            departmentNavigation.pop()
        }
//        router.replaceCurrent(Config.Main)

    }

    @Parcelize
    private sealed class DepartmentConfig(
//        val index: Int,
//        val isBackEnabled: Boolean,
    ) : Parcelable {
        object Department : DepartmentConfig()

        object AddDepartment : DepartmentConfig()
    }
}

