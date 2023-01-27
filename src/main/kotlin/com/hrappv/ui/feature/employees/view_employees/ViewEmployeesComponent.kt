package com.hrappv.ui.feature.employees.view_employees

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.hrappv.GetAllEmployees
import com.hrappv.di.AppComponent
import com.hrappv.ui.navigation.Component
import javax.inject.Inject

class ViewEmployeesComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onAddEmployee: () -> Unit,
    private val onEmployeeDetail: (emp: GetAllEmployees) -> Unit,
    private val onBackPress: () -> Unit,
) : Component, ComponentContext by componentContext {

    @Inject
    lateinit var viewModel: ViewEmpViewModel

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel) {
            viewModel.init(scope)
        }

        val backToMain by viewModel.backToMain.collectAsState()
        val startEmpDetails by viewModel.isEmpDetailsPressed.collectAsState()
        val startAddEmp by viewModel.isAddEmployeePressed.collectAsState()
        val empDetails by viewModel.empDetailsPressed.collectAsState()
//        val backToMain by mainViewModel.startEmpResult.collectAsState()


        if (startEmpDetails && !startAddEmp && !backToMain) {
            println("emp Details is $startEmpDetails")

            if (empDetails != null)
                onEmployeeDetail(empDetails)
        }

        if (startAddEmp && !startEmpDetails && !backToMain) {
            onAddEmployee()
        }



        if (backToMain) {
            println("startEmpResult is $backToMain")
            onBackPress()

        }

        ViewEmpScreen(viewModel)
    }
}