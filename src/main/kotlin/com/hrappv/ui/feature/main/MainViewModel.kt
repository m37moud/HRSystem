package com.hrappv.ui.feature.main

import com.hrappv.data.repo.MyRepo
import com.hrappv.ui.value.R
import com.hrappv.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val myRepo: MyRepo,
    // Inject your repos here...
) : ViewModel() {
    companion object {
        const val INIT_WELCOME_MSG = "Hello World!"
    }

    private val _welcomeText = MutableStateFlow(INIT_WELCOME_MSG)
    val welcomeText: StateFlow<String> = _welcomeText


    private val _window = MutableStateFlow(WindowState())
    val window: StateFlow<WindowState> = _window


    private val _isStartEmpResult = MutableStateFlow(false)
    val startEmpResult: StateFlow<Boolean> = _isStartEmpResult

    fun onClickMeClicked() {
        _welcomeText.value = myRepo.getClickedWelcomeText()
    }
    fun startHomeScreen() {
        _window.value = WindowState(R.string.HOME,)
    }fun startEmpResultScreen() {
        _window.value = WindowState(R.string.HOME,)
        _isStartEmpResult.value = true
    }

    fun onBackPress() {
        _isStartEmpResult.value = false
    }
}