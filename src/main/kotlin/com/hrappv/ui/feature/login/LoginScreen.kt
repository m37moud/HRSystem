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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.application
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Key
import compose.icons.fontawesomeicons.solid.User


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
        val error = viewModel.userAuthSate.value.error
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
    val passwordVisibility = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = userName,
        onValueChange = {
            userName = it
//            viewModel.emitError(null)
        },
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
        modifier = Modifier.testTag("username"),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            autoCorrect = true,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
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
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility.value = !passwordVisibility.value
            }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = if (passwordVisibility.value) painterResource("drawables/eye_open.png")
                    else painterResource("drawables/eye_close.png"),
                    contentDescription = "",
//                        tint = if (passwordVisibility.value) custom else Grey
                )
            }
        },

        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
        else PasswordVisualTransformation(),
        modifier = Modifier.onKeyEvent {
            if (it.key == Key.Enter) {
                viewModel.login(userName, password)
                true
            } else {
                false
            }
        }.testTag("password"),
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),

        )
    Spacer(modifier = Modifier.height(8.dp))

    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        val isChecked = remember { mutableStateOf(false) }

        Checkbox(
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it },
            enabled = true,
//            colors = CheckboxDefaults.colors(custom),
            modifier = Modifier
                .padding(5.dp)
                .size(3.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))


        Text(
            "remember password",
//            modifier = Modifier.width(320.dp),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
//            color = custom
        )
    }
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
