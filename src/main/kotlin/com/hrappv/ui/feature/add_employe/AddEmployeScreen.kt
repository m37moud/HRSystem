package com.hrappv.ui.feature.add_employe

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.hrappv.data.models.Employees
import com.hrappv.ui.value.HrAppVTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import util.Constatnts.Companion.excelImporter
import java.time.format.DateTimeFormatter


@Composable
fun AddEmployeeScreen(
    viewModel: AddEmployeViewModel
) {
    val formState = remember { mutableStateOf(EmployeeFormState()) }
    var path by remember { mutableStateOf("F:\\8") }

//    val path by remember { mutableStateOf("D:\\desk\\شغل لعهد\\tutorial audting\\2022\\شهراغسطس8\\8\\8") }
    val showPathDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    var employee: List<Employees> = emptyList()


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
                            employee = excelImporter(path).distinct()
                            println(employee.toString())

                            if (employee.isNotEmpty()){
                                viewModel.insertEmpFromImporter(employee)
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
                label = { Text("Employee ID") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = formState.value.fname,
                onValueChange = { formState.value.fname = it },
                label = { Text("First Name") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextFieldMenu3(name = "Department")


            Spacer(modifier = Modifier.height(8.dp))
//            MyDateField()

            TextField(
                value = formState.value.bith_day,
                onValueChange = { formState.value.bith_day = it },
                label = { Text("Birth Day") }
            )
            Spacer(modifier = Modifier.height(8.dp))

//           DatePicker(
//            selectedDate = formState.value.bith_day,
//            onDateSelected = { formState.value.bith_day = it }
//        )
            TextField(
                value = formState.value.salary,
                onValueChange = { formState.value.salary = it },
                label = { Text("Salary") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = formState.value.vacanition,
                onValueChange = { formState.value.vacanition = it },
                label = { Text("Vacation Days") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = formState.value.vbalance,
                onValueChange = { formState.value.vbalance = it },
                label = { Text("Vacation Balance") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = formState.value.bdl_balance,
                onValueChange = { formState.value.bdl_balance = it },
                label = { Text("bdl_balance") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = formState.value.totaldays,
                onValueChange = { formState.value.totaldays = it },
                label = { Text("Total Days") }
            )

//        TextField(
//            value = formState.value.depart_id,
//            onValueChange = { formState.value.depart_id = it },
//            label = { Text("Department ID") }
//        )
            Spacer(modifier = Modifier.height(8.dp))


//         DropdownMenu(
//            selectedOption = formState.value.depart_id,
//            onSelectedOptionChange = { formState.value.depart_id = it },
//            options = listOf("Option 1", "Option 2", "Option 3")
//        )
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                // Insert the new employee into the database here
            }) {
                Text("Add Employee")
            }
        }
    }

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
///*
//Let’s implement it. If you look at the output, there are 2 elements – TextField and menu. They are placed inside a box.
//
//Jetpack Compose provides two APIs:
//
//ExposedDropdownMenu – It is the menu that shows the items.
//ExposedDropdownMenuBox – It is the box that contains the TextField and the ExposedDropdownMenu.
//
//params
//expanded – Whether the menu is currently open and visible to the user.
//
//onDismissRequest – Called when the user requests to dismiss the menu, such as by tapping on the screen outside the menu’s bounds or pressing the back button.
//
//content – The content of the dropdown menu, typically a DropdownMenuItem
//*/
//
//@Composable
//fun ExposedDropdownMenu(
//    expanded: Boolean,
//    onDismissRequest: () -> Unit,
//    modifier: Modifier = Modifier,
//    content: @Composable ColumnScope.() -> Unit
//) {
//}
////
/////*
////params
////expanded – Whether the Dropdown Menu should be expanded or not.
////
////onExpandedChange – It is called when the user clicks on this menu box.
////
////content – The content to be displayed inside ExposedDropdownMenuBox. It typically contains TextField and ExposedDropdownMenu.
////*/
//
//@ExperimentalMaterialApi
//@Composable
//fun ExposedDropdownMenuBox(
//    expanded: Boolean,
//    onExpandedChange: (Boolean) -> Unit,
//    modifier: Modifier = Modifier,
//    content: @Composable ExposedDropdownMenuBoxScope.() -> Unit
//) {
//}
////
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
@Composable
fun TextFieldMenu2(modifier: Modifier = Modifier, name: String) {
var expanded by remember { mutableStateOf(false) }
val suggestions = listOf("Item1","Item2","Item3")
var selectedText by remember { mutableStateOf("") }

var textfieldSize by remember { mutableStateOf(Size.Zero)}

val icon = if (expanded)
    Icons.Filled.KeyboardArrowUp //it requires androidx.compose.material:material-icons-extended
else
    Icons.Filled.ArrowDropDown


Column() {
    OutlinedTextField(
        value = selectedText,
        onValueChange = { selectedText = it },
        modifier = Modifier
//            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                //This value is used to assign to the DropDown the same width
                textfieldSize = coordinates.size.toSize()
            },
        label = {Text("$name")},
        trailingIcon = {
            Icon(icon,"contentDescription",
                 Modifier.clickable { expanded = !expanded })
        }
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .width(with(LocalDensity.current){textfieldSize.width.toDp()})
    ) {
        suggestions.forEach { label ->
            DropdownMenuItem(onClick = {
                selectedText = label
            }) {
                Text(text = label)
            }
        }
    }
}
}

// in githup space in 19/1
@Composable
fun TextFieldMenu3(modifier: Modifier = Modifier, name: String) {
var expanded by remember { mutableStateOf(false) }
val suggestions = listOf("Item1","Item2","Item3")
var selectedText by remember { mutableStateOf("") }

var dropDownWidth by remember { mutableStateOf(0) }

val icon = if (expanded)
    Icons.Filled.KeyboardArrowUp
else
    Icons.Filled.ArrowDropDown


Column() {
    OutlinedTextField(
        value = selectedText,
        onValueChange = { selectedText = it },
        modifier = Modifier
            .onSizeChanged {
                dropDownWidth = it.width
            },
        readOnly = true,

        label = {Text("$name")},
        trailingIcon = {
            Icon(icon,"contentDescription", Modifier.clickable { expanded = !expanded })
        }
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
                .width(with(LocalDensity.current){dropDownWidth.toDp()})
    ) {
        suggestions.forEach { label ->
            DropdownMenuItem(onClick = {
                selectedText = label
                expanded = false

            }) {
                Text(text = label)
            }
        }
    }
}
}



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
