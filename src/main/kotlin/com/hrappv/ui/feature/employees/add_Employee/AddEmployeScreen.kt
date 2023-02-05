package com.hrappv.ui.feature.employees.add_Employee

import FileDialog
import androidx.compose.animation.animateContentSize
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hrappv.data.models.Employees
import com.hrappv.ui.components.MenuDropDown
import com.hrappv.ui.components.ShowMessageDialog
import com.hrappv.ui.value.HrAppVTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import util.Constatnts
import util.Constatnts.Companion.empExcelImporter


@Composable
fun AddEmployeeScreen(
    viewModel: AddEmployeViewModel
) {
    val departments = viewModel.departments.collectAsState(emptyList()).value
    val formState = remember { mutableStateOf(EmployeeFormState()) }
    var path by remember { mutableStateOf("") }
    var emp_id by remember { mutableStateOf("") }
    var fname by remember { mutableStateOf("") }
    var totaldays by remember { mutableStateOf("21") }
    var bith_day by remember { mutableStateOf("1990-11-16") }
    var salary by remember { mutableStateOf("1000") }
    var vacanition by remember { mutableStateOf("0") }
    var vbalance by remember { mutableStateOf("0") }
    var bdl_balance by remember { mutableStateOf("0") }
    var department by remember { mutableStateOf("") }

    var showMessageDialog by remember { mutableStateOf(false) }

//    val path by remember { mutableStateOf("D:\\desk\\شغل لعهد\\tutorial audting\\2022\\شهراغسطس8\\8\\8") }
    var showPathDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    var employee: List<Employees> = emptyList()

    val insertResult = viewModel.insertEmp.collectAsState()


    /***
     * import
     */
    var startImporter by remember { mutableStateOf(false) }
    var showWarningDialog by remember { mutableStateOf(false) }

    println("startImporter = $startImporter")

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
            cancel = { showMessageDialog = false },
            message = insertResult.value
        )
    }

    /**
     * show chose file dialog
     *
     */
    if (showPathDialog) {
        FileDialog(title = "Select Folder", isLoad = true, onResult = {
            if (it.toString().isNotEmpty()) path = it.toString()
            showPathDialog = false
        })
    }


    /**
     * show Import excel message dialog
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
                    startImporter = false
                    viewModel.onBackPress()
                }

            },
            cancel = {
                showWarningDialog = false
            },
            message = "Import Departments from Excel File is Running cancel ?"
        )
    }



    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                title = {
                    Text("Add New Employee")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (!startImporter)
                            viewModel.onBackPress()
                        else
                            showWarningDialog = true
                    }
                    ) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back Button")
                    }
                })
        },
        content = {
            Surface {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier.padding(start = 6.dp, end = 6.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = 10.dp
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
                                    modifier = Modifier.padding(16.dp),
                                    // Provide a custom shape for this button. In this example. we specify the button to have
                                    // round corners of 16dp radius.
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
                                                startImporter = true
                                                employee = empExcelImporter(path).distinct()
                                                println(employee.toString())

                                                if (employee.isNotEmpty()) {
                                                    startImporter = scope.launch(Dispatchers.IO)
                                                    {
                                                        viewModel.insertEmpFromImporter(employee)
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
                                    placeholder = { Text("put url excel folder") },
                                    label = { Text(text = "paste here ...") },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Filled.Refresh,
                                            "search location"
                                        )
                                    },
                                    trailingIcon = {
                                        if (path != "") {
                                            IconButton(
                                                onClick = {
//                                                    onCloseSearch()

                                                    path = "" // Remove text from TextField when you press the 'X' icon
                                                }
                                            ) {
                                                Icon(
                                                    Icons.Default.Close,
                                                    contentDescription = "",
                                                    modifier = Modifier
                                                        .padding(15.dp)
                                                        .size(24.dp)
                                                )
                                            }
                                        }
                                    },
                                    shape = RoundedCornerShape(16.dp),

                                    )

                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    /**
                     * insert employee
                     */
                    Column(
                        //  modifier = Modifier.fillMaxSize(),
                        modifier = Modifier.verticalScroll(state = rememberScrollState(0), enabled = true),
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {

                        OutlinedTextField(
                            value = emp_id,
                            onValueChange = { emp_id = it },
                            label = { Text("Employee ID") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = true,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = fname,
                            onValueChange = { fname = it },
                            label = { Text("Employee Name") }, singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = true,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        MenuDropDown(
                            name = "Select Department", departments = departments,
                            menuItemSelected = { depart ->
                                if (depart != null) {
                                    department = if (depart.department == "Select Department") {
                                        ""
                                    } else {
                                        depart.department

                                    }


                                }
                            }
                        )


                        Spacer(modifier = Modifier.height(8.dp))
//            MyDateField()

                        OutlinedTextField(
                            value = bith_day,
                            onValueChange = { bith_day = it },
                            label = { Text("Birth Date") }, singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = true,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                        )
                        Spacer(modifier = Modifier.height(8.dp))

//           DatePicker(
//            selectedDate = formState.value.bith_day,
//            onDateSelected = { formState.value.bith_day = it }
//        )
                        OutlinedTextField(
                            value = salary,
                            onValueChange = { salary = it },
                            label = { Text("Salary") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = true,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = vacanition,
                            onValueChange = { vacanition = it },
                            label = { Text("Vacation Days") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = true,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = vbalance,
                            onValueChange = { vbalance = it },
                            label = { Text("Vacation Balance") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = true,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = bdl_balance,
                            onValueChange = { bdl_balance = it },
                            label = { Text("bdl_balance") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = true,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = totaldays,
                            onValueChange = { totaldays = it },
                            label = { Text("Total Days") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = true,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            )
                        )

//        TextField(
//            value = formState.value.depart_id,
//            onValueChange = { formState.value.depart_id = it },
//            label = { Text("Department ID") }
//        )

                        Spacer(modifier = Modifier.height(8.dp))


                        Button(onClick = {
                            if (emp_id.isNotEmpty() && department.isNotEmpty()) {

                                Employees(
                                    emp_id.trim().toLong(),
                                    fname,
                                    department,
                                    totaldays.toLong(),
                                    bith_day,
                                    salary.toFloat(),
                                    vacanition.toLong(),
                                    vbalance.toLong(),
                                    bdl_balance.toLong()

                                ).apply {
                                    println("with apply emp " + this)
                                    viewModel.insertNewEmp(this)

                                }
                            } else {
                                viewModel.dialogMessage("You need to add (Employee Id and Department)")

                            }


                            // Insert the new employee into the database here
                        }) {
                            Text("Add Employee")
                        }
                    }

                }
            }
        },
        bottomBar = {

            /**
             * show linear loading
             */
            if (startImporter) {

                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.onPrimary
                )

            }
        }

    )


}


@Composable
@Preview
fun FavoriteCollectionCardPreview() {
    val darkTheme by remember { mutableStateOf(false) }
    HrAppVTheme(darkTheme) {
//        AddEmployeeScreen()
    }
}


@Composable
fun ReadonlyTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: @Composable () -> Unit
) {
    Box {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            label = label
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = onClick),
        )
    }
}

//
//@Composable
//fun MyDateField() {
//    val dialogState = rememberMaterialDialogState()
//    val textState = remember { mutableStateOf(TextFieldValue()) }
//    val dialog = MaterialDialog(dialogState, buttons = {
//        positiveButton("Ok")
//        negativeButton("Cancel")
//    }){
//        datepicker { date ->
////            val formattedDate = date.(
////                DateTimeFormatter.ofPattern("dd.MM.yyyy")
////            )
//            textState.value = TextFieldValue(text = date.toString())
//        }
//    }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        ReadonlyTextField(
//            value = textState.value,
//            onValueChange = { textState.value = it },
//            onClick = {
//                dialogState.show()
//            },
//            label = {
//                Text(text = "Date")
//            }
//        )
//    }
//}

//
//
////
////fun imageFromFile(file: File): ImageAsset {
////    return org.jetbrains.skia.Image.makeFromEncoded(file.readBytes()).asImageAsset()
////}
//
//
//
///*
//val file = File("D:\\images\\my_image.PNG")
//   val image = remember { imageFromFile(file) }
//
//   Image(asset = image)
//
//
//   or
//
//val file = File(path)
//val imageBitmap: ImageBitmap = remember(file) {
//    loadImageBitmap(file.inputStream())
//}
//
//Image(
//    painter = BitmapPainter(image = imageBitmap),
//    contentDescription = null
//)
//*/
//
//
///*
//how to use
//
//  AsyncImage(
//            load = { loadImageBitmap(File("sample.png")) },
//            painterFor = { remember { BitmapPainter(it) } },
//            contentDescription = "Sample",
//            modifier = Modifier.width(200.dp)
//        )
//*/
//
//@Composable
//fun <T> AsyncImage(
//    load: suspend () -> T,
//    painterFor: @Composable (T) -> Painter,
//    contentDescription: String,
//    modifier: Modifier = Modifier,
//    contentScale: ContentScale = ContentScale.Fit,
//) {
//    val image: T? by produceState<T?>(null) {
//        value = withContext(Dispatchers.IO) {
//            try {
//                load()
//            } catch (e: IOException) {
//                // instead of printing to console, you can also write this to log,
//                // or show some error placeholder
//                e.printStackTrace()
//                null
//            }
//        }
//    }
//
//    if (image != null) {
//        Image(
//            painter = painterFor(image!!),
//            contentDescription = contentDescription,
//            contentScale = contentScale,
//            modifier = modifier
//        )
//    }
//}
//
//
//// final code
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextFieldMenu(modifier: Modifier = Modifier, name: String) {

//    val contextForToast = LocalContext.current.applicationContext

    val listItems = arrayOf("Favorites", "Options", "Settings", "Share")

    var selectedItem by remember {
        mutableStateOf(listItems[0])
    }

    var expanded by remember {
        mutableStateOf(false)
    }

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


// in githup space in 19/1


//
//
//
//// for edit textfield
//OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun MyUI() {
//
//    val listItems = arrayOf("Favorites", "Options", "Settings", "Share")
//
//    var selectedItem by remember {
//        mutableStateOf("")
//    }
//
//    var expanded by remember {
//        mutableStateOf(false)
//    }
//
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = {
//            expanded = !expanded
//        }
//    ) {
//
//        TextField(
//            value = selectedItem,
//            onValueChange = { selectedItem = it },
//            label = { Text(text = "Label") },
//            trailingIcon = {
//                ExposedDropdownMenuDefaults.TrailingIcon(
//                    expanded = expanded
//                )
//            },
//            colors = ExposedDropdownMenuDefaults.textFieldColors()
//        )
//
//        // filter options based on text field value
//        val filteringOptions =
//            listItems.filter { it.contains(selectedItem, ignoreCase = true) }
//
//        if (filteringOptions.isNotEmpty()) {
//
//            ExposedDropdownMenu(
//                expanded = expanded,
//                onDismissRequest = { expanded = false }
//            ) {
//                filteringOptions.forEach { selectionOption ->
//                    DropdownMenuItem(
//                        onClick = {
//                            selectedItem = selectionOption
//                            expanded = false
//                        }
//                    ) {
//                        Text(text = selectionOption)
//                    }
//                }
//            }
//        }
//    }
//}
