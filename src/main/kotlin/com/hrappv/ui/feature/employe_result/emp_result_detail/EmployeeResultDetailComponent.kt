package com.hrappv.ui.feature.employe_result.emp_result_detail

import androidx.compose.runtime.*
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
import com.hrappv.ui.feature.employe_result.emp_result_detail.details_days.DaysDetailComponent
import com.hrappv.ui.feature.employe_result.emp_result_detail.absent_days.AbsentDaysDetailComponent
import com.hrappv.ui.navigation.Component
import com.toxicbakery.logging.Arbor
import javax.inject.Inject

class EmployeeResultDetailComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val employeeResult: EmployeeResult,

    private val onBackPress: () -> Unit,
) : Component, ComponentContext by componentContext {

    private val appComponentM: AppComponent = DaggerAppComponent
        .create()
    @Inject
    lateinit var viewModel: EmployeeResultDetailViewModel

    init {
        appComponent.inject(this)
    }

    /**
     * Router configuration
     */
    private val resultBarNavigation = StackNavigation<DayResultBarConfig>()
    private val resultBarStack = childStack(
        source = resultBarNavigation,
        initialConfiguration = DayResultBarConfig.Days,
        handleBackButton = true,
        key = "resultBarStack",
        childFactory = ::createScreenComponent,

        )
    /**
     * When a new navigation request made, the screen will be created by this method.
     */
    private fun createScreenComponent(config: DayResultBarConfig, componentContext: ComponentContext): Component {
        return when (config) {

            is DayResultBarConfig.Days -> DaysDetailComponent(
                appComponent = appComponentM,
                componentContext = componentContext,
                onDaysClick = ::startDaysDetailScreen,
                employeeResult = employeeResult

            )

            is DayResultBarConfig.AbsentDays -> AbsentDaysDetailComponent(
                appComponent = appComponentM,
                onAbsentClick = ::startAbsentDaysDetailScreen,
                componentContext = componentContext,
                employeeResult = employeeResult


            )


        }
    }

    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun render() {
        val childStack: Value<ChildStack<*, Component>> = resultBarStack
        val child = childStack.subscribeAsState()
        val activeComponent= child.value.active.instance
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel) {
            viewModel.init(scope)
        }


        val back by viewModel.back.collectAsState()



        if (back) {
            onBackPress()
        }
        EmpResultDetails(viewModel, employeeResult ,
            activeComponent = activeComponent,
            onDaysClick = {
            startDaysDetailScreen()
        },
        onAbsentDaysClick = {
            startAbsentDaysDetailScreen()
        }){


            Arbor.d(activeComponent.toString())

            Children(
                stack = resultBarStack,
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
    }

    private fun startDaysDetailScreen() {
        if (resultBarStack.value.active.configuration !is DayResultBarConfig.Days) {

            resultBarNavigation.bringToFront(DayResultBarConfig.Days)
        }
    }

    private fun startAbsentDaysDetailScreen() {
        if (resultBarStack.value.active.configuration !is DayResultBarConfig.AbsentDays) {

            resultBarNavigation.bringToFront(DayResultBarConfig.AbsentDays)
        }
    }
    @Parcelize
    private sealed class DayResultBarConfig(
    ) : Parcelable {
        object Days : DayResultBarConfig()


        object AbsentDays : DayResultBarConfig()
    }
}