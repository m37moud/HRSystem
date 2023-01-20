package com.hrappv.ui.feature.add_department

import FileDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import com.hrappv.GetEmployeeByName
import com.hrappv.data.models.Department
import com.hrappv.ui.feature.add_employe.EmployeeFormState
import com.hrappv.ui.value.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import util.Constatnts
import utils.LCE

@Composable
fun DepartmentScreen(viewModel: DepartmentViewModel) {
    var path by remember { mutableStateOf("F:\\8") }

    val showPathDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val departmentResult = viewModel.departResults.collectAsState().value
    val departments = viewModel.departments.collectAsState(emptyList()).value

    var departmentList: List<Department> = emptyList()


    /*
    https://github.com/vinaygaba/Learn-Jetpack-Compose-By-Example
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(items = personList,
            itemContent = { index, person ->
                // AnimatedVisibility is a pre-defined composable that automatically animates the 
                // appearace and disappearance of it's content. This makes it super easy to animated 
                // things like insertion/deletion of a list element. The visible property tells the
                // AnimatedVisibility about whether to show the composable that it wraps (in this case, 
                // the Card that you see below). This is where you can add logic about whether a certain 
                // element needs to either be shown or not. In our case, we want to show an element, only
                // if its not a part of the deletedPersonList list. As this list changes and a given 
                // person is either shown or hidden from the screen, the "enter" and "exit" animations 
                // are called for a given item. AnimatedVisibility also let's you specify the enter and 
                // exit animation so that you have full control over how you'd like to animate it's enter
                // or exit. In the example below, since I also added functionality to delete an item, I 
                // customize the exit animation to be an animation that shrinks vertically and gave the 
                // animation a duration of 1000ms. 
                AnimatedVisibility(
                    visible = !deletedPersonList.contains(person),
                    enter = expandVertically(),
                    exit = shrinkVertically(
                        animationSpec = tween(
                            durationMillis = 1000,
                        )
                    )
                ) {
                    // Card composable is a predefined composable that is meant to represent the 
                    // card surface as specified by the Material Design specification. We also 
                    // configure it to have rounded corners and apply a modifier.
                    Card(
                        shape = RoundedCornerShape(4.dp),
                        backgroundColor = colors[index % colors.size],
                        modifier = Modifier.fillParentMaxWidth()
                    ) {
                        // Row is a composable that places its children in a horizontal sequence. You
                        // can think of it similar to a LinearLayout with the horizontal orientation.
                        Row(
                            modifier = Modifier.fillParentMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Text is a predefined composable that does exactly what you'd expect it to -
                            // display text on the screen. It allows you to customize its appearance using
                            // the style property.
                            Text(
                                person.name, style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center
                                ), modifier = Modifier.padding(16.dp)
                            )
                            IconButton(
                                // When this button is clicked, we add the person to deletedPersonList.
                                onClick = {
                                    deletedPersonList.add(person)
                                }
                            ) {
                                // Simple composable that allows you to draw an icon on the screen. It
                                // accepts a vector asset as the icon.
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete"
                                )
                            }
                        }
                    }
                }
            })
    }
}
    */

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
                    Button(
                        onClick = {
                            scope.launch(Dispatchers.IO) {
//                                FileDialog(title = "", isLoad = true, onResult = {})

                                departmentList = Constatnts.excelImporterDepartment(path).distinct()
                                println(departmentList.toString())
                                if (departmentList.isNotEmpty()) {
                                    viewModel.insertDepartmentFromImporter(departmentList)
                                }
                            }
                        },
                        modifier = Modifier.padding(16.dp),
                        // Provide a custom shape for this button. In this example. we specify the button to have
                        // round corners of 16dp radius.
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.elevation(5.dp),
                    ) {
                        Text(text = "Import From Excel")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            viewModel.setDepartments(departments)

            when (val state = departmentResult) {

                is LCE.LOADING -> LoadingUI()

                is LCE.CONTENT -> {
                    ContentUI(
                        state.data,
//                        allEmployee.value,
                    ) { id ->
                        try {

//                                viewEmployee.getSingleEmployee(id)

//                            viewModel.deleteEmployee(id)
//
                        } catch (exception: Exception) {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Delete failed")
                            }
                            println(exception.message)

                        }
                    }
                }

                is LCE.ERROR -> ErrorUI(state.error)
                else -> {}
            }

        }
    }

}

@Composable
private fun ContentUI(
    data: List<Department>,
    onDeleteClick: (id: Long) -> Unit,


    ) {

    EmployeeLazyColumn(data, onDeleteClick)


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmployeeLazyColumn(
    data: List<Department>,
    onDeleteClick: (id: Long) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
//            .horizontalScroll(rememberScrollState())
        ,
        horizontalAlignment = Alignment.CenterHorizontally // this can change for verticalAlignment
    ) {
        Header()

        LazyColumn(
            modifier = Modifier.fillMaxSize()
//                .padding(vertical = 4.dp) // this can change for contentPadding = PaddingValues(horizontal = 16.dp)
            , contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(items = data, itemContent =  { department ->
                AnimatedVisibility(
                    visible = true//!deletedPersonList.contains(person),
                    ,enter = expandVertically(),
                    exit = shrinkVertically(
                        animationSpec = tween(
                            durationMillis = 1000,
                        )
                    )
                ){
                    EmployeeCardMenu(
                    Modifier.animateItemPlacement(
                        tween(durationMillis = 1000)
                    ),
                    department,
                )}



            })
        }


    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Card(
        backgroundColor =
        Color.LightGray,
        modifier = Modifier.padding(horizontal = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Item(text = "ID", width = 55.dp)
            Item(text = "Name", width = 450.dp)
            Item(text = "Commetion rate", width = 200.dp)
            Item(text = "Department type", width = 200.dp)
            Item(text = "Commetion type ", width = 200.dp)
            Item(text = "Commetion month", width = 200.dp)
            Spacer(Modifier.weight(1f).fillMaxWidth())
        }
    }
}

@Composable
@Preview
fun EmployeeCardMenu(
    modifier: Modifier = Modifier,
    department: Department,
) {
    Card(
        backgroundColor =
        Color.LightGray,
//        .cardColors(containerColor = MaterialTheme.colors.primary)
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        EmployeeItemMenu(department)
    }

}

@Composable
@Preview
fun EmployeeItemMenu(
    department: Department,
) {


    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween //this can change for Arrangement.spacedBy()

    ) {
        Item(text = department.depart_id.toString(), width = 50.dp)
        Item(text = department.department, width = 450.dp)
        Item(text = department.commetion_rate.toString(), width = 200.dp)
        Item(text = department.depart_type.toString(), width = 200.dp)
        Item(text = department.commetion_type.toString(), width = 200.dp)
        Item(text = department.commetion_month.toString(), width = 200.dp)

        Spacer(Modifier.weight(1f).fillMaxWidth())
        Button(onClick = {}, modifier = Modifier.align(Alignment.Bottom)) {
            Icon(Icons.Filled.AccountBox, "show employee detail")
        }
    }


}

@Composable
fun Item(text: String, width: Dp) {
    val modifierText = Modifier.padding(5.dp).height(35.dp)

    Card(
        elevation = 10.dp,
//        backgroundColor =
//        Color.LightGray,
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Text(
            maxLines = 1,
            textAlign = TextAlign.Center,
            text = text,
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraBold),
            modifier = modifierText.width(width),
        )
    }
}

@Composable
fun ErrorUI(error: String = "Something went wrong, try again in a few minutes. ¯\\_(ツ)_/¯") {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = error,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 72.dp, vertical = 72.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.error,
        )
    }
}

@Composable
fun LoadingUI() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .defaultMinSize(minWidth = 96.dp, minHeight = 96.dp),
        )
    }
}


@Composable
fun AddSingleDepartment() {
    val formState = remember { mutableStateOf(EmployeeFormState()) }

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
