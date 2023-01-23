package com.hrappv.ui.feature.add_department

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrappv.data.models.Department


@Composable
fun AddDepartmentScreen(viewModel: AddDepartmentViewModel) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState, topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.onSecondary,
                title = {
                    Text(text = "Register Attends")
                }, navigationIcon = {
                    IconButton(onClick = { viewModel.onBackPress() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                }
            )
        }
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

//                    TextField(
//                        value = formState.value.depart_id.toString(),
//                        onValueChange = { formState.value.depart_id = it.toLong() },
//                        label = { Text("Department ID") }
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))

//                    formState.value.department?.let {
                TextField(
                    value = "it",
                    onValueChange = { }, //formState.value.department = it
                    label = { Text("Department Name") }
                )
//                    }
                Spacer(modifier = Modifier.height(8.dp))
//            TextFieldMenu()


                TextField(
                    value = "formState.value.commetion_rate.toString()",
                    onValueChange = { }, //formState.value.commetion_rate = it.toLong()
                    label = { Text("Department Commetion Type") }
                )
                Spacer(modifier = Modifier.height(8.dp))

//                    formState.value.commetion_type?.let {
                TextFieldMenu(name = "it") {
//                            formState.value.commetion_type = it
                }
//                    }

                Spacer(modifier = Modifier.height(8.dp))

//                    formState.value.commetion_month?.let {
//                        TextField(
//                            value = "it",
//                            onValueChange = {
////                                formState.value.commetion_month = it
//                                            },
//                            label = { Text("Department Commetion Month") }
//                        )
//                    }
                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {
//                onResult(formState.value)
//                    viewModel.insertDepartment(Department(department = "test"))
//                        onClose()
                    // Insert the new employee into the database here
                }) {
                    Text("Add Department")
                }
            }
        }
    }


}