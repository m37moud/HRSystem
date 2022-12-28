package com.hrappv.ui.security

import kotlinx.coroutines.flow.MutableStateFlow

data class UserAuthSate (val auth : Boolean = false , val error:String? = "" ,val username : String = "UnknownError")
//     val auth = MutableStateFlow(false)
//     val error = MutableStateFlow<String?>(null)


