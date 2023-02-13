package com.hrappv.ui.feature.employe_result.emp_result_detail.absent_days

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.hrappv.data.models.EmployeeResult
import com.hrappv.di.AppComponent
import com.hrappv.ui.navigation.Component
import javax.inject.Inject

class AbsentDaysDetailComponent
    (
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onAbsentClick: () -> Unit,
    private val employeeResult: EmployeeResult,
//    private val onBackPress: () -> Unit
) : Component, ComponentContext by componentContext {
    @Inject
    lateinit var viewModel: AbsentDaysDetailViewModel
    init {
        appComponent.inject(this)
    }
    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel){
            viewModel.init(scope)
        }


        onAbsentClick()


        AbsentDaysDetails(viewModel,employeeResult)
    }
}