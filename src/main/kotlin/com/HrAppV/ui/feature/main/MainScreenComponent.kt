package com.HrAppV.ui.feature.main

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.HrAppV.di.AppComponent
import com.HrAppV.ui.navigation.Component
import javax.inject.Inject

class MainScreenComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onClickEmpResult: () ->Unit
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
        val startEmpResultScreen by viewModel.startEmpResult.collectAsState()

        if (startEmpResultScreen)
        {
            println("startEmpResult is $startEmpResultScreen")
            onClickEmpResult()
        }
        MainScreen2(viewModel)
    }
}