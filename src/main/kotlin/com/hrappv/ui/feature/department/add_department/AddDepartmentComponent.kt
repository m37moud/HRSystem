package com.hrappv.ui.feature.department.add_department

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.hrappv.di.AppComponent
import com.hrappv.ui.navigation.Component
import javax.inject.Inject

class AddDepartmentComponent (
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onBackPress: () -> Unit
) : Component, ComponentContext by componentContext {

    @Inject
    lateinit var viewModel: AddDepartmentViewModel
//

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel){
            viewModel.init(scope)



        }

        val backToMain by viewModel.backToMain.collectAsState()


        if (backToMain) {
            println("backToMain add department is $backToMain")
            onBackPress()

        }


        AddDepartmentScreen(viewModel)

    }
}