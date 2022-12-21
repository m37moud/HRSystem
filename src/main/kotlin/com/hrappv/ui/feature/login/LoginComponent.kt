package com.hrappv.ui.feature.login

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.hrappv.di.AppComponent
import com.hrappv.ui.navigation.Component
import javax.inject.Inject


//LoginComponent
class LoginComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onClickEmpResult: () ->Unit
) : Component, ComponentContext by componentContext {
    @Inject
    lateinit var viewModel: LoginViewModel

    init {
//        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel) {
            viewModel.init(scope)
        }
        val authenticated by viewModel.userAuthenticated.collectAsState()

        if (authenticated)
        {
            println("userAuthenticated is $authenticated")
            onClickEmpResult()
        }

//        AppLoginWindow(viewModel)
    }
}