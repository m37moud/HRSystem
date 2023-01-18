package com.hrappv.ui.feature.add_department

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hrappv.data.models.Department
import com.hrappv.data.models.Employees
import com.hrappv.ui.feature.add_employe.EmployeeFormState
import com.hrappv.ui.feature.add_employe.TextFieldMenu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import util.Constatnts

@Composable
fun DepartmentScreen(viewModel: DepartmentViewModel){
    val formState = remember { mutableStateOf(EmployeeFormState()) }
    var path by remember { mutableStateOf("F:\\8") }

    val showPathDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    var departmentList : List<Department> = emptyList()

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
        ) {
            Card(Modifier.fillMaxWidth(), elevation = 8.dp) {
                Row(modifier = Modifier.align(Alignment.End))
                {

//                    if (showPathDialog) {
//                        FileDialog(title = "Select Folder", isLoad = true) {
//                            path.value = it
//                        }
//                    }
                    OutlinedTextField(
                        value = path,
                        onValueChange = { path = it },
                        modifier = Modifier.padding(end = 16.dp).weight(1f),
                        placeholder = { Text("put url excel folder") },
                        label = { Text(text = "paste here ...") },
                        leadingIcon = { Icon(Icons.Filled.Refresh, "search location") }

                    )
                    Button(onClick = {
                        scope.launch(Dispatchers.IO) {
                            departmentList =  Constatnts.excelImporterDepartment(path).distinct()
                            println(departmentList.toString())
                            if (departmentList.isNotEmpty()){
                                viewModel.insertDepartmentFromImporter(departmentList)
                            }
                        }
                    }) {
                        Text(text = "Import From Excel")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = formState.value.emp_id,
                onValueChange = { formState.value.emp_id = it },
                label = { Text("Department ID") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = formState.value.fname,
                onValueChange = { formState.value.fname = it },
                label = { Text("Department Name") }
            )
            Spacer(modifier = Modifier.height(8.dp))
//            TextFieldMenu()


            Spacer(modifier = Modifier.height(8.dp))

            TextFieldMenu(name = "Department Type")

//           DatePicker(
//            selectedDate = formState.value.bith_day,
//            onDateSelected = { formState.value.bith_day = it }
//        )
            TextField(
                value = formState.value.salary,
                onValueChange = { formState.value.salary = it },
                label = { Text("Department Commetion Type") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = formState.value.vacanition,
                onValueChange = { formState.value.vacanition = it },
                label = { Text("Department Commetion Month") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                // Insert the new employee into the database here
            }) {
                Text("Add Department")
            }
        }
    }

}


@Composable
fun TextFieldMenu(modifier: Modifier = Modifier, name: String) {


    val listItems = arrayOf("Favorites", "Options", "Settings", "Share")

    var selectedItem by remember {
        mutableStateOf(listItems[0])
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    // the box
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = {
//            expanded = !expanded
//        }
//    ) {

    Column(modifier = modifier) {
        // text field
        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = "$name") },
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
//            colors = DropdownMenuDefaults.textFieldColors()
            // modifier = Modifier.clickable {
            //      expanded = !expanded
            // }
        )

        // menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listItems.forEach { selectedOption ->
                // menu item
                DropdownMenuItem(onClick = {
                    selectedItem = selectedOption
                    expanded = false
                }) {
                    Text(text = selectedOption)
                }
            }
        }
    }
}