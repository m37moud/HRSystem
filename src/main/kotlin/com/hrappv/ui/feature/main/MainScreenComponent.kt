package com.hrappv.ui.feature.main

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.hrappv.di.AppComponent
import com.hrappv.ui.feature.login.LoginViewModel
import com.hrappv.ui.navigation.Component
import com.hrappv.ui.security.UserAuthSate
import com.hrappv.ui.value.R
import javax.inject.Inject

class MainScreenComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val userAuthState: UserAuthSate,
    private val onClickHome: () -> Unit,
    private val onClickEmpResult: () -> Unit,
) : Component, ComponentContext by componentContext {
    @Inject
    lateinit var viewModel: MainViewModel

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel) {
            viewModel.init(scope)
        }
        val window by viewModel.window.collectAsState()
        println("startEmpResult is ${window.title}")
        when (window.title) {
            R.string.HOME -> onClickHome()
            R.string.EMPLOYE_RESULT -> onClickEmpResult()


        }
//        if (startEmpResultScreen)
//        {
//            println("startEmpResult is $startEmpResultScreen")
//            onClickEmpResult()
//        }
//        onUserLogOut(authenticated)

//        MainScreen2(viewModel,loginViewModel,userAuthState)
//        MainScreen2(viewModel,userAuthState)
//        MainScreen2(viewModel)
    }
}