package com.HrAppV.ui.feature.EmployeResult

import androidx.compose.runtime.*
import com.HrAppV.data.di.module.EmployeeResult
import com.HrAppV.di.AppComponent
import com.HrAppV.ui.feature.main.MainViewModel
import com.HrAppV.ui.navigation.Component
import com.arkivanov.decompose.ComponentContext
import javax.inject.Inject

class EmpolyeResultScreenComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onClickEmpResult: () ->Unit
): Component, ComponentContext by componentContext {
    @Inject
    lateinit var viewModel: EmployeeResultViewModel

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel) {
            viewModel.init(scope)
        }
//        val folderPath by viewModel.folderPath.collectAsState()
//        if(folderPath.isNotBlank()){
//
//        }


        EmployeeResultScreen(viewModel)
    }
}