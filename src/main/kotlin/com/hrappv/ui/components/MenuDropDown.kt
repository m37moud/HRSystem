package com.hrappv.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hrappv.data.models.Department

@Composable
fun MenuDropDown(
    modifier: Modifier = Modifier,
    name: String,
    departments: List<Department>,
    menuItemSelected: (Department?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    var dropDownWidth by remember { mutableStateOf(0) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.ArrowDropDown


    Column(
//        modifier = if (expanded) {
//            Modifier.height(200.dp).verticalScroll(
//                state = rememberScrollState(0), enabled = true
//            )
//        } else Modifier
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
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
            trailingIcon = {
                Icon(icon, "contentDescription", Modifier.clickable { expanded = !expanded })
            }
        ,
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.verticalScroll(state = rememberScrollState(0), enabled = true)
                .height(200.dp)
                .width(with(LocalDensity.current) { dropDownWidth.toDp() })
        ) {
            DropdownMenuItem(onClick = {
                selectedText = "All Departments"
                menuItemSelected(Department())
                expanded = false

            }) {
                Text(text = "All Departments")
            }
            departments.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label.department
                    menuItemSelected(label)
                    expanded = false

                }) {
                    Text(text = label.department)
                }
            }
        }
    }
}