package com.hrappv.ui.feature.department.add_department

import FileDialog
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hrappv.data.models.Department
import com.hrappv.ui.components.ShowMessageDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import util.Constatnts.Companion.excelImporterDepartment


@Composable
fun AddDepartmentScreen(viewModel: AddDepartmentViewModel) {
    val scope = rememberCoroutineScope()

    var showPathDialog by remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()

    var path by remember { mutableStateOf("") }

    var dName by remember { mutableStateOf("") }
    var dRate by remember { mutableStateOf("") }
    var dType by remember { mutableStateOf("") }

    var startImport by remember { mutableStateOf(false) }


    var departmentList: List<Department> = emptyList()
    var showWarningDialog by remember { mutableStateOf(false) }



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState, topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                title = {
                    Text(text = "Add Department")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (!startImport) {
                            viewModel.onBackPress()
//                            viewModel.dialogMessage()
                        } else {
                            showWarningDialog = true
//                            scope.launch { scaffoldState.snackbarHostState.showSnackbar("Import Departments from Excel File is Running contanuie " , actionLabel = "ok" , ) }


                            // viewModel.dialogMessage("Import Departments from Excel File is Running")

                        }

                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                }
            )
        },
        content = {

            /**
             * show chose file dialog
             */

            if (showPathDialog) {
                FileDialog(
//                                showDialogState = { showPathDialog = false },
                    title = "Select Folder",
                    isLoad = true
                ) {
                    if (it.toString().isNotEmpty()) path = it.toString()
                    showPathDialog = false
                }
            }

            /**
             * show add message dialog
             */

            val insertResult = viewModel.insertEmp.collectAsState()
            var showMessageDialog by remember { mutableStateOf(false) }


            if (insertResult.value.isNotEmpty()) {
                showMessageDialog = true


            }


            if (showMessageDialog) {
                ShowMessageDialog(
                    onDismissRequest = {
                        viewModel.dialogMessage()//close
                        showMessageDialog = false
                    }, ok = {
                        viewModel.dialogMessage()
                        showMessageDialog = false
                    },
                    cancel = {
                        viewModel.dialogMessage()
                        showMessageDialog = false
                    },
                    message = insertResult.value
                )
            }

            /**
             * show import excel message dialog
             */

            if (showWarningDialog) {
                ShowMessageDialog(
                    onDismissRequest = {
                        viewModel.dialogMessage()//close
                        showWarningDialog = false
                    }, ok = {
                        if (scope.isActive) {
                            scope.cancel()
                            showWarningDialog = false
                            startImport = false
                            viewModel.onBackPress()
                        }

                    },
                    cancel = {
                        showWarningDialog = false
                    },
                    message = "Import Departments from Excel File is Running cancel ?"
                )
            }



            Surface(modifier = Modifier.fillMaxSize()) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top
                ) {
                    /**
                     * top app bar
                     */
                    Card(
                        modifier = Modifier.padding(start = 6.dp, end = 6.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = 10.dp
                    ) {
                        Column(
                            modifier = Modifier
//                                .border(
//                                shape = RectangleShape,
//                                border = BorderStroke(2.dp, MaterialTheme.colors.onPrimary)
//                            )
                                .padding(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, bottom = 6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.align(Alignment.CenterStart)
                                        .animateContentSize(),
                                    verticalAlignment = Alignment.CenterVertically
                                )
                                {

                                    Button(
                                        onClick = {
                                            showPathDialog = true
                                        },
                                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        elevation = ButtonDefaults.elevation(5.dp),
                                    ) {
                                        Text(text = "Import From Excel")
                                    }


                                    /**
                                     * if path text field is not empty anew button will appear
                                     */
                                    if (path.isNotEmpty()) {
                                        Button(
                                            onClick = {

                                               scope.launch(Dispatchers.IO) {
                                                startImport = true

                                                    departmentList = excelImporterDepartment(path).distinct()
                                                    println(departmentList.toString())
                                                    if (departmentList.isNotEmpty()) {
                                                        startImport = scope.launch(Dispatchers.IO) {
                                                            viewModel.insertDepartmentFromImporter(departmentList)
                                                        }.isCompleted
                                                    }
                                                }


                                            },
                                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                                            // Provide a custom shape for this button. In this example. we specify the button to have
                                            // round corners of 16dp radius.
                                            shape = RoundedCornerShape(16.dp),
                                            elevation = ButtonDefaults.elevation(5.dp),
                                        ) {
                                            Icon(imageVector = Icons.Outlined.AddCircle, contentDescription = null)
                                        }
                                    }



                                    OutlinedTextField(
                                        value = path,
                                        onValueChange = { path = it },

                                        modifier = Modifier.padding(end = 16.dp),
                                        placeholder = { Text("Select Excel Folder") },
                                        label = { Text(text = "Folder Path ...") },
                                        leadingIcon = { Icon(Icons.Filled.Refresh, "search location") },
                                        shape = RoundedCornerShape(16.dp),

//                            elevation = ButtonDefaults.elevation(5.dp),

                                    )
                                }

                            }

                        }


                    }

                    Column(
                        Modifier.padding(top = 8.dp).verticalScroll(state = rememberScrollState(0), enabled = true),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        OutlinedTextField(
                            value = dName,
                            onValueChange = { dName = it }, //formState.value.department = it
                            label = { Text("Department Name") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = true,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
//            TextFieldMenu()


                        OutlinedTextField(
                            value = "$dRate",
                            onValueChange = { dRate = it }, //formState.value.commetion_rate = it.toLong()
                            label = { Text("Commetion Rate") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = true,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                        )
                        Spacer(modifier = Modifier.height(8.dp))

//                    formState.value.commetion_type?.let {
                        TextFieldMenu(name = "Department Type") {
                            dType = it
                        }
//                    }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(onClick = {

                            if (dName.isNotEmpty()) {
                                Department(
                                    department = dName,
                                    commetion_rate = if (dRate.isNotEmpty()) {
                                        dRate.toLong()
                                    } else {
                                        15
                                    },
                                    depart_type = dType
                                ).apply { viewModel.insertDepartment(this) }

                            } else {
                                viewModel.dialogMessage("You need to add ( Department Name )")

                            }

//                        onClose()
                            // Insert the new employee into the database here
                        }) {
                            Text("Add Department")
                        }

                    }

//
                }
            }
        },
        bottomBar = {

            /**
             * show linear loading
             */
            if (startImport) {

                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.onPrimary
                )

            }
        }

    )


}

@Composable
private fun TextFieldMenu(modifier: Modifier = Modifier, name: String, onValue: (String) -> Unit) {


    val listItems = arrayOf("ادارى", "صناعى")

    var selectedItem by remember {
        mutableStateOf(listItems[0])
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    Column(modifier = modifier) {
        // text field
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { onValue },
            readOnly = true,
            label = { Text(text = "$name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                IconButton(onClick = {
                    expanded = !expanded
                }) {
                    Icon(
                        imageVector =

                        if (expanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.ArrowDropDown,
                        contentDescription =

                        if (expanded) "show less" else "show more", modifier = Modifier.size(24.dp)
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