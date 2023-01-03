package com.hrappv.ui.feature.home_screen

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.hrappv.di.AppComponent
import com.hrappv.ui.feature.main.MainScreen2
import com.hrappv.ui.feature.main.MainViewModel
import com.hrappv.ui.navigation.Component
import com.hrappv.ui.security.UserAuthSate
import javax.inject.Inject

class HomeComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
//    private val userAuthState: UserAuthSate,
    private val onBackPress: () ->Unit,
) : Component, ComponentContext by componentContext {
    @Inject
    lateinit var homeViewModel: HomeViewModel
    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(homeViewModel) {
            homeViewModel.init(scope)
        }
//        val startEmpResultScreen by homeViewModel.startEmpResult.collectAsState()
//
//        if (startEmpResultScreen)
//        {
//            println("startEmpResult is $startEmpResultScreen")
//            onClickEmpResult()
//        }
//        onUserLogOut(authenticated)

//        HomeContent(homeViewModel,userAuthState)
        HomeContent(homeViewModel)
    }

}