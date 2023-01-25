package com.hrappv.ui.feature.department.show_departments

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.hrappv.di.AppComponent
import com.hrappv.ui.navigation.Component
import javax.inject.Inject

class DepartmentComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onAddDepartment: () -> Unit,
    private val onBackPress: () -> Unit,
) : Component, ComponentContext by componentContext {

    @Inject
    lateinit var viewModel: DepartmentViewModel
//
//    @Inject
//    lateinit var mainViewModel: EditEmployeViewModel

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
        val addDepartment by viewModel.iSAddDepartmentPressed.collectAsState()



        if (addDepartment&&!backToMain) {
            println("addDepartment is $addDepartment")
            viewModel.closeDepart()
            onAddDepartment()

        }

        if (backToMain) {
            println("back is $backToMain")
            onBackPress()

        }

        DepartmentScreen(
            viewModel
        )
    }
}