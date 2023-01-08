package com.hrappv.ui.feature.add_employe

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.hrappv.ui.value.HrAppVTheme


@Composable
fun AddEmployeeScreen(
//    viewModel: AddEmployeViewModel
) {
    val formState = remember { mutableStateOf(EmployeeFormState()) }

    Column {
        TextField(
            value = formState.value.emp_id,
            onValueChange = { formState.value.emp_id = it },
            label = { Text("Employee ID") }
        )
        TextField(
            value = formState.value.fname,
            onValueChange = { formState.value.fname = it },
            label = { Text("First Name") }
        )
        TextField(
            value = formState.value.totaldays,
            onValueChange = { formState.value.totaldays = it },
            label = { Text("Total Days") }
        )
        TextField(
            value = formState.value.bith_day,
            onValueChange = { formState.value.bith_day = it },
            label = { Text("Birth Day") }
        )
        TextField(
            value = formState.value.salary,
            onValueChange = { formState.value.salary = it },
            label = { Text("Salary") }
        )
        TextField(
            value = formState.value.vacanition,
            onValueChange = { formState.value.vacanition = it },
            label = { Text("Vacation Days") }
        )
        TextField(
            value = formState.value.vbalance,
            onValueChange = { formState.value.vbalance = it },
            label = { Text("Vacation Balance") }
        )
        TextField(
            value = formState.value.bdl_balance,
            onValueChange = { formState.value.bdl_balance = it },
            label = { Text("Borrowed Leave Balance") }
        )
        TextField(
            value = formState.value.depart_id,
            onValueChange = { formState.value.depart_id = it },
            label = { Text("Department ID") }
        )
        Button(onClick = {
            // Insert the new employee into the database here
        }) {
            Text("Add Employee")
        }
    }

}



@Composable
@Preview
fun FavoriteCollectionCardPreview() {
    val darkTheme by remember { mutableStateOf(false) }
    HrAppVTheme(darkTheme) {
        AddEmployeeScreen()
    }
}