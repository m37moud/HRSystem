package com.hrappv.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.hrappv.ui.security.UserAuthSate
import com.hrappv.ui.value.R

/**
 * Available screensSelectApp
 */

sealed class Config(
    val label: String,
    val icon: ImageVector? = null
) : Parcelable {
    //    object Splash : Config()
//    object Login : Config(
//        label = R.string.HOME,
//        icon = Icons.Rounded.Home
//    )

//    data class Main(val userAuthState: UserAuthSate) : Config(
//        label = R.string.HOME,
//        icon = Icons.Rounded.Home
//    )
@Parcelize
    object Home : Config(
        label = R.string.HOME,
        icon = Icons.Rounded.Home
    )
    object MainDepartment : Config(
        label = R.string.DEPARTMENT,
        icon = Icons.Rounded.Home
    )

    @Parcelize
    object AddEmployee : Config(
        label = R.string.ADD_EMPLOYE,
        icon = Icons.Rounded.Home
    )
    @Parcelize
    object ViewEmployee : Config(
        label = R.string.ADD_EMPLOYE,
        icon = Icons.Rounded.Home
    )

    //    object EmployeResult : Screens(
//        label = R.string.EMPLOYE_RESULT,
//        icon = Icons.Rounded.Home
//    )
//
    @Parcelize
    object RegisterAttends : Config(
        label = R.string.REGISTER_ATTENDS,
        icon = Icons.Rounded.Home
    )

    @Parcelize
    object Settings : Config(
        label = R.string.SETTINGS,
        icon = Icons.Rounded.Home
    )
    @Parcelize
    object About : Config(
        label = R.string.ABOUT,
        icon = Icons.Rounded.Home
    )

    //
//    object LogOut : Config(
//        label = R.string.LOG_OUT,
//        icon = Icons.Rounded.Home
//    )

    @Parcelize
    data class Main(val userAuthState: UserAuthSate) : Config(
        label = R.string.LOG_OUT,
        icon = Icons.Rounded.Home
    )


}