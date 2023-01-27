package com.hrappv.ui.feature.employees

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
import com.hrappv.GetAllEmployees
import com.hrappv.di.AppComponent
import com.hrappv.di.DaggerAppComponent
import com.hrappv.ui.feature.employees.add_Employee.AddEmployeScreenComponent
import com.hrappv.ui.feature.employees.employee_details.EmployeeDetailsComponent
import com.hrappv.ui.feature.employees.view_employees.ViewEmployeesComponent
import com.hrappv.ui.navigation.Component

class DefaultViewEmpComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onBackPress: () -> Unit
) : Component, ComponentContext by componentContext {

    private val appComponent: AppComponent = DaggerAppComponent
        .create()

    private val employeesNavigation = StackNavigation<ViewEmpConfig>()
    private val employeeStack = childStack(
        source = employeesNavigation,
        initialConfiguration = ViewEmpConfig.ViewEmployees,
        handleBackButton = true,
        key = "employeeStack",
        childFactory = ::createScreenComponent,

        )


    private fun createScreenComponent(config: ViewEmpConfig, componentContext: ComponentContext): Component {
        return when (config) {

            is ViewEmpConfig.ViewEmployees -> ViewEmployeesComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onAddEmployee = ::startAddEmployeeScreen,
                onEmployeeDetail = ::startEmployeeDetailsScreen,
                onBackPress = ::onBackPress

            )

            is ViewEmpConfig.AddEmp -> AddEmployeScreenComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onBackPress = ::onBackPress

            )

            is ViewEmpConfig.EmpDetails -> EmployeeDetailsComponent(
                appComponent = appComponent,
                employee = config.emp,
                componentContext = componentContext,

                onBackPress = ::onBackPress

            )


        }
    }


    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun render() {
        val childStack: Value<ChildStack<*, Component>> = employeeStack
        val child = childStack.subscribeAsState()
        val activeComponent = child.value.active.instance

        println(activeComponent.toString())

        Children(
            stack = employeeStack,
//            animation = stackAnimation(), //fade() + scale()
            animation = stackAnimation { _, _, direction ->
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


    private fun startAddEmployeeScreen() {
//        router.replaceCurrent(Config.Home)
        if (employeeStack.value.active.configuration !is ViewEmpConfig.AddEmp) {

            employeesNavigation.replaceCurrent(ViewEmpConfig.AddEmp)
        }
    }

    private fun startEmployeeDetailsScreen(emp: GetAllEmployees) {
        if (employeeStack.value.active.configuration !is ViewEmpConfig.EmpDetails) {

            employeesNavigation.replaceCurrent(ViewEmpConfig.EmpDetails(emp))
        }
    }

    private fun onBackPress() {
//        navigation.replaceCurrent(Config.Department)
        if (employeeStack.value.active.configuration !is ViewEmpConfig.ViewEmployees) {

            employeesNavigation.replaceCurrent(ViewEmpConfig.ViewEmployees)
        }
//        router.replaceCurrent(Config.Main)

    }


    @Parcelize
    private sealed class ViewEmpConfig(
//        val index: Int,
//        val isBackEnabled: Boolean,
    ) : Parcelable {
        @Parcelize
        object ViewEmployees : ViewEmpConfig()

        @Parcelize
        object AddEmp : ViewEmpConfig()

        @Parcelize
        data class EmpDetails(val emp: GetAllEmployees) : ViewEmpConfig()
    }
}