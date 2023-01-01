package com.hrappv.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.decompose.statekeeper.Parcelable
import com.hrappv.ui.security.UserAuthSate
import com.hrappv.ui.value.R

sealed class Config(
    val label: String,
    val icon: ImageVector? = null
) : Parcelable {
//    object Splash : Config()
//    object Login : Config()

    //    data class Main(val userAuthState: UserAuthSate) : Config()

    object Main : Config(
        label = R.string.HOME,
        icon = Icons.Rounded.Home
    )

    object AddEmployee : Config(
        label = R.string.ADD_EMPLOYE,
        icon = Icons.Rounded.Home
    )

    object RegisterAttends : Config(
        label = R.string.REGISTER_ATTENDS,
        icon = Icons.Rounded.Home
    )
    object EmployeResult : Config(
        label = R.string.HOME,
        icon = Icons.Rounded.Home
    )


    object Settings : Config(
        label = R.string.SETTINGS,
        icon = Icons.Rounded.Home
    )

    object About : Config(
        label = R.string.ABOUT,
        icon = Icons.Rounded.Home
    )

    object LogOut : Config(
        label = R.string.LOG_OUT,
        icon = Icons.Rounded.Home
    )


}