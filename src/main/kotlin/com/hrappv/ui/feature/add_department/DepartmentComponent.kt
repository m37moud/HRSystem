package com.hrappv.ui.feature.add_department

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.hrappv.di.AppComponent
import com.hrappv.ui.feature.add_employe.AddEmployeViewModel
import com.hrappv.ui.feature.add_employe.AddEmployeeScreen
import com.hrappv.ui.navigation.Component
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DepartmentComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onBackPress: () -> Unit
) : Component, ComponentContext by componentContext {

    private val _isBackPressed = MutableStateFlow(false)
    val backToMain: StateFlow<Boolean> = _isBackPressed

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


        if (backToMain) {
            println("startEmpResult is $backToMain")
            onBackPress()

        }

        DepartmentScreen(
            viewModel
        )
    }
}