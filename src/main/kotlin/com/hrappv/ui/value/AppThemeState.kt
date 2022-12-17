package com.hrappv.ui.value

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class AppThemeState {
    private val themeState: MutableState<Boolean> = mutableStateOf(false)
    var isDarkTheme: Boolean = false
        get() = themeState.value
        set(value) {
            field = value
            themeState.value = field
        }
}

@Composable
fun rememberAppThemeState(): AppThemeState = remember {
    AppThemeState()
}