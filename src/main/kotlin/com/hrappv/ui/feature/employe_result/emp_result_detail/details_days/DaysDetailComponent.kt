package com.hrappv.ui.feature.employe_result.emp_result_detail.details_days

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.hrappv.data.models.EmployeeResult
import com.hrappv.di.AppComponent
import com.hrappv.ui.navigation.Component
import javax.inject.Inject

class DaysDetailComponent (
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onDaysClick: () -> Unit,
    private val employeeResult: EmployeeResult,

) : Component, ComponentContext by componentContext  {
    @Inject
    lateinit var viewModel: DaysDetailViewModel
    init {
        appComponent.inject(this)
    }
    @Composable
    override fun render() {
       val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel){
            viewModel.init(scope)
        }


        onDaysClick()


        DaysDetails(viewModel,employeeResult)
    }
}