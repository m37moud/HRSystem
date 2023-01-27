package com.hrappv.ui.feature.employees.add_Employee

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.hrappv.di.AppComponent
import com.hrappv.ui.navigation.Component
import javax.inject.Inject


class AddEmployeScreenComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onBackPress: () ->Unit
): Component, ComponentContext by componentContext {

    @Inject
    lateinit var viewModel: AddEmployeViewModel
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
//            mainViewModel.init(scope)
        }
//        val folderPath by viewModel.folderPath.collectAsState()
//        if(folderPath.isNotBlank()){
//
//        }
        val backToMain by viewModel.backToMain.collectAsState()
//        val backToMain by mainViewModel.startEmpResult.collectAsState()


        if (backToMain)
        {
            println("startEmpResult is $backToMain")
            onBackPress()

        }

        AddEmployeeScreen(
            viewModel
        )
    }

}