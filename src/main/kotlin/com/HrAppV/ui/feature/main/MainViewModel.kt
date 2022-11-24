package com.HrAppV.ui.feature.main

import com.HrAppV.data.repo.MyRepo
import com.HrAppV.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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


    private val _isStartEmpResult = MutableStateFlow(false)
    val startEmpResult: StateFlow<Boolean> = _isStartEmpResult

    fun onClickMeClicked() {
        _welcomeText.value = myRepo.getClickedWelcomeText()
    }
    fun startEmpResultScreen() {
        _isStartEmpResult.value = true
    }
}