package com.hrappv.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hrappv.data.models.Department

@Composable
fun DepartMenuDropDown(
    modifier: Modifier = Modifier,
    name: String,
    departments: List<Department>,
    menuItemSelected: (Department?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("All Departments") }

    var dropDownWidth by remember { mutableStateOf(0) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.ArrowDropDown


    Column(
        modifier = modifier
//        modifier = if (expanded) {
//            Modifier.height(200.dp).verticalScroll(
//                state = rememberScrollState(0), enabled = true
//            )
//        } else Modifier
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
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
            label = { Text("Department") },
//            trailingIcon = {
//                Icon(icon, "contentDescription", Modifier.clickable { expanded = !expanded })
//            },
            trailingIcon = {
                IconButton(onClick = {
                    expanded = !expanded
                }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = if (expanded) "show less" else "show more"
                    )
                }
            },
        )
        val stateVertical = rememberScrollState(0)
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .height(200.dp)
                    .width(
                        with(LocalDensity.current) {
                            dropDownWidth.toDp()
                        })
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
//            VerticalScrollbar(
//                modifier = Modifier
////                    .align(Alignment.CenterEnd)
//                    .fillMaxHeight(),
//                adapter = rememberScrollbarAdapter(stateVertical)
//            )
        }
}


