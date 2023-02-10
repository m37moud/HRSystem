package com.hrappv.ui.feature.employe_result

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
import com.hrappv.data.models.EmployeeResult
import com.hrappv.di.AppComponent
import com.hrappv.di.DaggerAppComponent
import com.hrappv.ui.feature.department.DefaultDepartmentComponent
import com.hrappv.ui.feature.department.add_department.AddDepartmentComponent
import com.hrappv.ui.feature.department.show_departments.DepartmentComponent
import com.hrappv.ui.feature.employe_result.emp_result_detail.EmployeeResultDetailComponent
import com.hrappv.ui.feature.employe_result.register_attends.EmployResultScreenComponent
import com.hrappv.ui.navigation.Component

class DefaultResultComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onBackPress: () -> Unit
) : Component, ComponentContext by componentContext  {

    private val appComponent: AppComponent = DaggerAppComponent
        .create()

    private val resultNavigation = StackNavigation<DayResultConfig>()
    private val resultStack = childStack(
        source = resultNavigation,
        initialConfiguration = DayResultConfig.EmployeeResults,
        handleBackButton = true,
        key = "resultStack",
        childFactory = ::createScreenComponent,

        )
    private fun createScreenComponent(config: DayResultConfig, componentContext: ComponentContext): Component {
        return when (config) {

            is DayResultConfig.EmployeeResults -> EmployResultScreenComponent(
                appComponent = appComponent,
                componentContext = componentContext,
                onResultDetail = ::startAddEmpResultDetailScreen,
                onBackPress = ::onBackPress

            )

            is DayResultConfig.EmployeeResultsDetails -> EmployeeResultDetailComponent(
                appComponent = appComponent,
                employeeResult = config.result,
                componentContext = componentContext,
                onBackPress = ::onBackPress

            )


        }
    }

    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun render() {
        val childStack: Value<ChildStack<*, Component>> = resultStack
        val child = childStack.subscribeAsState()
        val activeComponent= child.value.active.instance

        println(activeComponent.toString())

        Children(
            stack = resultStack,
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

    private fun startAddEmpResultDetailScreen(result : EmployeeResult) {
        if (resultStack.value.active.configuration !is DayResultConfig.EmployeeResultsDetails) {

            resultNavigation.replaceCurrent(DayResultConfig.EmployeeResultsDetails(result))
        }
    }

    private fun onBackPress() {
//        navigation.replaceCurrent(Config.Department)
        if (resultStack.value.active.configuration !is DayResultConfig.EmployeeResults) {

            resultNavigation.replaceCurrent(DayResultConfig.EmployeeResults)
        }
//        router.replaceCurrent(Config.Main)

    }
    @Parcelize
    private sealed class DayResultConfig(
    ) : Parcelable {
        object EmployeeResults : DayResultConfig()

        data class EmployeeResultsDetails(val result : EmployeeResult) : DayResultConfig()
    }
}