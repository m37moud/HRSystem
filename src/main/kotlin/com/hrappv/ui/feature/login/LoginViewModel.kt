package com.hrappv.ui.feature.login

import com.hrappv.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {


    private val _isUserAuthenticated = MutableStateFlow(false)
    val userAuthenticated: StateFlow<Boolean> = _isUserAuthenticated

    private val _errorState = MutableStateFlow<String?>(null)
    val  errorState : StateFlow<String?> = _errorState

    fun login(user: String?, pass: String?) {
        launchOnIO {
            try {
//                val authReq = UsernamePasswordAuthenticationToken(user, pass)
//                val auth: Authentication = authManager.authenticate(authReq)
//                val sc = SecurityContextHolder.getContext()
//                sc.authentication = auth
//                _isUserAuthenticated.auth.emit(auth.isAuthenticated)
                _isUserAuthenticated.emit(true)
            } catch (e: Exception) {
                _errorState.emit(e.message)
            }
        }
    }


    fun emitError(error: String?) {
        launchOnIO {
            _errorState.emit(null)
        }
    }
}