package com.hrappv.ui.feature.employees.employee_details

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.hrappv.GetAllEmployees
import com.hrappv.di.AppComponent
import com.hrappv.ui.navigation.Component
import javax.inject.Inject

class EmployeeDetailsComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val employee: GetAllEmployees,
    private val onBackPress: () -> Unit
) : Component, ComponentContext by componentContext {

    @Inject
    lateinit var viewModel: EmployeeDetailsViewModel

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel) {
            viewModel.init(scope)
        }

        val back by viewModel.back.collectAsState()

        if (back) {
            onBackPress()
        }

        EmployeeDetails(viewModel, employee)

    }
}