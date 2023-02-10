package com.hrappv.ui.feature.employe_result.register_attends

import androidx.compose.runtime.*
import com.hrappv.di.AppComponent
import com.hrappv.ui.feature.main.MainViewModel
import com.hrappv.ui.navigation.Component
import com.arkivanov.decompose.ComponentContext
import com.hrappv.data.models.EmployeeResult
import com.toxicbakery.logging.Arbor
import javax.inject.Inject

class EmployResultScreenComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onResultDetail: (result: EmployeeResult) -> Unit,
    private val onBackPress: () -> Unit
) : Component, ComponentContext by componentContext {
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
        val back by viewModel.backToMain.collectAsState()

        val resultDetails by viewModel.resultDetailsPressed.collectAsState()

        val startEmpDetails by viewModel.isResultDetailsPressed.collectAsState()

        if (startEmpDetails && !back) {
            if (resultDetails != null) {
                Arbor.d(resultDetails!!.name)
                onResultDetail(resultDetails!!)
            }
        }


        if (back && !startEmpDetails) {
            Arbor.d("startEmpResult is $back")
            onBackPress()

        }

        EmployeeResultScreen(viewModel,)
    }
}