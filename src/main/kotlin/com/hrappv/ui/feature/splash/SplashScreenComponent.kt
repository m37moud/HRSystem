package com.hrappv.ui.feature.splash

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.hrappv.di.AppComponent
import com.hrappv.ui.navigation.Component
import javax.inject.Inject

class SplashScreenComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onSplashFinished: () -> Unit,
) : Component, ComponentContext by componentContext {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {

        val scope = rememberCoroutineScope()
        LaunchedEffect(splashViewModel) {
            splashViewModel.init(scope)
        }

        val isSplashFinished by splashViewModel.isSplashFinished.collectAsState()

        if (isSplashFinished) {
            println("isSplashFinished is $isSplashFinished")

            onSplashFinished()
        }

        SplashScreen(
            viewModel = splashViewModel
        )
    }
}