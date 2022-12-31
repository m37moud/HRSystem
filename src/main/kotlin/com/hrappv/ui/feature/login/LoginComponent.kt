package com.hrappv.ui.feature.login

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.hrappv.di.AppComponent
import com.hrappv.ui.navigation.Component
import com.hrappv.ui.security.UserAuthSate
import javax.inject.Inject
import kotlin.reflect.KFunction1


//LoginComponent
class LoginComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onUserAuthentcated: (UserAuthSate)->Unit
) : Component, ComponentContext by componentContext {
    @Inject
    lateinit var viewModel: LoginViewModel

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel) {
            viewModel.init(scope)
        }
        val authenticated by viewModel.userAuthSate.collectAsState()
//        val authenticated by viewModel.userAuthenticated.collectAsState()

        if (authenticated.auth) {
            println("userAuthenticated is $authenticated")
            onUserAuthentcated(authenticated)
        } else {
            println("userAuthenticated is $authenticated")

        }
//        application {
//            AppLoginWindow(viewModel)
//        }
        LoginScreen(viewModel)
    }
}