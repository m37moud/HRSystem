package com.hrappv.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldMenu(
    modifier: Modifier = Modifier,
    name: String,
    listItems: List<*> = listOf("-", "-", "-", "-"),
    menuItemSelected: (String?) -> Unit
) {

    var dropDownWidth by remember { mutableStateOf(0) }

    var selectedItem by remember {
        mutableStateOf(listItems[0])
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    Column(modifier = modifier) {
        // text field
        OutlinedTextField(
            value = selectedItem.toString(),
            onValueChange = { selectedItem = it },
            modifier = modifier
                .onSizeChanged {
                    dropDownWidth = it.width
                },
            readOnly = true,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            label = { Text(name) },
//            trailingIcon = {
//                Icon(icon, "contentDescription", Modifier.clickable { expanded = !expanded })
//            },
            trailingIcon = {
                IconButton(onClick = {
                    expanded = !expanded
                }) {
                    Icon(
                        imageVector =

                        if (expanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.ArrowDropDown,
                        contentDescription =

                        if (expanded) "show less" else "show more"
                    )
                }
            },
        )

        // menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = modifier
                .verticalScroll(
                    state = rememberScrollState(0),
                    enabled = true
                )
                .height(200.dp)
                .width(with(LocalDensity.current) {
                    dropDownWidth.toDp()
                })
        ) {
            listItems.forEach { selectedOption ->
                // menu item
                DropdownMenuItem(onClick = {
                    selectedItem = selectedOption
                    menuItemSelected(selectedOption.toString())

                    expanded = false
                }) {
                    Text(text = selectedOption.toString())
                }
            }
        }
    }
}

