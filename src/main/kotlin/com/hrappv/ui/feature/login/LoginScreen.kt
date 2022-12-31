package com.hrappv.ui.feature.login

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.window.Window
import com.hrappv.ui.value.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.PasswordVisualTransformation
//import compose.icons.FontAwesomeIcons
//import compose.icons.fontawesomeicons.Solid
//import compose.icons.fontawesomeicons.solid.Key
//import compose.icons.fontawesomeicons.solid.User
import androidx.compose.ui.input.key.key
import androidx.compose.ui.window.application
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Key
import compose.icons.fontawesomeicons.solid.User


@Composable //ApplicationScope.
 fun ApplicationScope.AppLoginWindow(viewModel: LoginViewModel) {

    // parameter -> applicationContext: ApplicationContext
    val loginWindowState = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        width = 400.dp,
        height = 600.dp,
    )
    Window(
        onCloseRequest = ::exitApplication
        ,
        state = loginWindowState,
        resizable = false,
        title = R.string.LOGIN,
        icon = painterResource("drawables/logo.png")
    ) {
        CompositionLocalProvider(
            LocalLayoutDirection provides LayoutDirection.Ltr
        ) {
            LoginScreen(viewModel)
        }
    }
}


@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Image(
            painter = painterResource("drawables/launcher_icons/system.png"),
            contentDescription = "app-logo",
            modifier = Modifier.size(
                80.dp
            ).clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "HR App System",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(50.dp))

        LoginForm(viewModel)

//        val error by viewModel.userAuthSate.collectAsState()
        val error= viewModel.userAuthSate.value.error
        if (!error.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = error,
                fontSize = 14.sp,
                color = Color.Red
            )
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun LoginForm(viewModel: LoginViewModel) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    OutlinedTextField(
        value = userName,
        onValueChange = {
            userName = it
//            viewModel.emitError(null)
        },
        singleLine = true,
        label = {
            Text(R.string.USER_NAME)
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = FontAwesomeIcons.Solid.User,
                contentDescription = null
            )
        },
        modifier = Modifier.testTag("username")
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = password,
        onValueChange = {
            password = it
//            viewModel.emitError(null)
        },
        singleLine = true,
        label = {
            Text(R.string.PASSWORD)
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = FontAwesomeIcons.Solid.Key,
                contentDescription = null
            )
        },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.onKeyEvent {
            if (it.key == Key.Enter) {
                viewModel.login(userName, password)
                true
            } else {
                false
            }
        }.testTag("password")
    )
    Spacer(modifier = Modifier.height(8.dp))

    Button(
        modifier = Modifier.testTag("login-button"),
        onClick = {
            viewModel.login(userName, password)
        }
    ) {
        Text(R.string.LOGIN)
    }
}