package com.hrappv.ui.feature.settings

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.hrappv.di.AppComponent
import com.hrappv.ui.navigation.Component

class SettingsComponent (
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
//    private val userAuthState: UserAuthSate,
    private val onBackPress: () ->Unit,
) : Component, ComponentContext by componentContext  {
    init {
        appComponent.inject(this)
    }
    @Composable
    override fun render() {
        SettingsScreen()
    }

}