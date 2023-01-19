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
                    Button(onClick = {
                        scope.launch(Dispatchers.IO) {
                            departmentList =  Constatnts.excelImporterDepartment(path).distinct()
                            println(departmentList.toString())
                            if (departmentList.isNotEmpty()){
                                viewModel.insertDepartmentFromImporter(departmentList)
                            }
                        }
                    }
                         ,modifier = Modifier.padding(16.dp),
        // Provide a custom shape for this button. In this example. we specify the button to have
        // round corners of 16dp radius.
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.elevation(5.dp),) {
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
