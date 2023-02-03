package com.hrappv.ui.feature.employees.employee_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrappv.GetAllEmployees

@Composable
fun EmployeeDetails(viewModel: EmployeeDetailsViewModel, employee: GetAllEmployees?) {

    val scaffoldState = rememberScaffoldState()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.surface ,
            title = {
                Text(employee!!.fname)
                    }, navigationIcon = {
            IconButton(onClick = {viewModel.onBackPress()}){
                Icon(imageVector = Icons.Outlined.ArrowBack , contentDescription = "back button")
            }
        })
    }, content = {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box {
                Text("$employee")
            }

        }
    }
    )


}