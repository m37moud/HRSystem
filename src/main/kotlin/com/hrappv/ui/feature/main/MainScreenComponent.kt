package com.hrappv.ui.feature.main

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.hrappv.di.AppComponent
import com.hrappv.ui.feature.login.LoginViewModel
import com.hrappv.ui.navigation.Component
import com.hrappv.ui.security.UserAuthSate
import javax.inject.Inject

class MainScreenComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val userAuthState: UserAuthSate,
    private val onClickEmpResult: () ->Unit,
) : Component, ComponentContext by componentContext {
    @Inject
    lateinit var viewModel: MainViewModel
    @Inject
    lateinit var loginViewModel: LoginViewModel

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel) {
            viewModel.init(scope)
        }
        val startEmpResultScreen by viewModel.startEmpResult.collectAsState()

        if (startEmpResultScreen)
        {
            println("startEmpResult is $startEmpResultScreen")
            onClickEmpResult()
        }
//        onUserLogOut(authenticated)

        MainScreen2(viewModel,loginViewModel,userAuthState)
    }
}