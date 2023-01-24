package com.hrappv.ui.feature.add_department

import FileDialog
import androidx.compose.animation.animateContentSize
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hrappv.data.models.Department
import com.hrappv.ui.components.ShowMessageDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import util.Constatnts
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

    var departmentList: List<Department> = emptyList()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState, topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.onSecondary,
                title = {
                    Text(text = "Add Department")
                }, navigationIcon = {
                    IconButton(onClick = { viewModel.onBackPress() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                }
            )
        }
    ) {

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
                },
                message = insertResult.value
            )
        }


        Surface(modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
                Card(
//                modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp),
                    elevation = 8.dp
                ) {


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp)
                    ) {
                        Row(modifier = Modifier.align(Alignment.CenterEnd).animateContentSize(),
                            verticalAlignment = Alignment.CenterVertically)
                        {


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

                            if (path.isNotEmpty()) {
                                Button(
                                    onClick = {

                                        scope.launch(Dispatchers.IO) {

                                            departmentList = excelImporterDepartment(path).distinct()
                                            println(departmentList.toString())
                                            if (departmentList.isNotEmpty()) {
                                                viewModel.insertDepartmentFromImporter(departmentList)
                                            }
                                        }


                                    },
                                    modifier = Modifier.padding(start = 8.dp ,end = 8.dp),
                                    // Provide a custom shape for this button. In this example. we specify the button to have
                                    // round corners of 16dp radius.
                                    shape = RoundedCornerShape(16.dp),
                                    elevation = ButtonDefaults.elevation(5.dp),
                                ) {
                                    Icon(imageVector = Icons.Outlined.AddCircle, contentDescription = null)
                                }
                            }


                            Button(
                                onClick = {
                                    showPathDialog = true
                                },
                                modifier = Modifier.padding(start = 8.dp ,end = 8.dp),
                                shape = RoundedCornerShape(16.dp),
                                elevation = ButtonDefaults.elevation(5.dp),
                            ) {
                                Text(text = "Import From Excel")
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
                        Department(
                            department = dName, commetion_rate = dRate.toLong(),
                            depart_type = dType
                        ).apply { viewModel.insertDepartment(this) }

//                        onClose()
                        // Insert the new employee into the database here
                    }) {
                        Text("Add Department")
                    }

                }

//
            }
        }
    }


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