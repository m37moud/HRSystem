@file:OptIn(ExperimentalComposeUiApi::class)

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrappv.Department
import com.hrappv.GetEmployeeByName
import com.hrappv.ui.components.MenuDropDown
import com.hrappv.ui.feature.view_employees.ViewEmpViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CashRegister
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import utils.LCE

//https://johncodeos.com/how-to-add-search-in-list-with-jetpack-compose/


@Composable
fun ViewEmpScreen(viewEmployee: ViewEmpViewModel) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val textState = remember { mutableStateOf(TextFieldValue("")) }
//    val textState =viewEmployee.queries.collectAsState()
    var deletedEmpName = remember { mutableStateOf("-") }




    val empName = textState.value.text
//    val empName = textState.value
    viewEmployee.getQueries(empName)

    val departments = viewEmployee.departments.collectAsState(emptyList()).value

    val employees = viewEmployee.empResults.collectAsState()
//    val allEmployee = viewEmployee.allEmployees.collectAsState(emptyList()).value
    val deleteEmployee = viewEmployee.employee.collectAsState()
    deletedEmpName.value = deleteEmployee.value.fname


    var selectedGrid by remember { mutableStateOf(true) }
    var selectedMenu by remember { mutableStateOf(false) }


    val deleteEmpState = viewEmployee.delete.collectAsState()
    if (deleteEmpState.value) {
        scope.launch { scaffoldState.snackbarHostState.showSnackbar("Employee ${deletedEmpName.value} deleted Sucssessful") }
    }

    /*
    add this to make search with short cut 
      onKeyEvent = {
        if (
            it.isCtrlPressed &&
            it.isShiftPressed &&
            it.key == Key.C &&
            it.type == KeyEventType.KeyDown
        ) {
            cleared = true
            true
        } else {
            false
        }
    }
    **** or tyy add shortcut 
    shortcut = KeyShortcut(Key.C, ctrl = true)
    */
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 12.dp),
                elevation = 10.dp,
                backgroundColor = MaterialTheme.colors.onBackground
            ) {
                Row {
                    Spacer(Modifier.width(12.dp))
                    Card(
                        modifier = Modifier.padding(8.dp),
                        elevation = 10.dp,
//                    backgroundColor = MaterialTheme.colors.onBackground
                    )
                    {
                        Text(
                            text = "Employees Number : ${viewEmployee.empNumber.value.toString()}",
                            modifier = Modifier.padding(10.dp)
                        )
                    }

                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            ) {


                SearchView(modifier = Modifier.align(Alignment.CenterStart),
                    name = "All Departments",departments=departments,
                    state = textState,menuItemSelected = {id->
                        id?.let { it1 -> viewEmployee.getEmployeesByDepartment(it1.depart_id!!) }

                    }, onpressEnterSearch = {
//                search(viewEmployee, empName)
                        viewEmployee.getEmployees(empName)
                    }) {
//                search(viewEmployee, "")
                    viewEmployee.getEmployees("")
                }






                selectPreviewBtn(
                    Modifier.align(Alignment.CenterEnd),
                    selectGrid = selectedGrid,
                    selectMenu = selectedMenu,
                    selectedGrid = {
                        selectedGrid = true
                        selectedMenu = false
                    },
                    selectedMenu = {
                        selectedMenu = true
                        selectedGrid = false
                    }
                )

            }



            when (val state = employees.value) {
                is LCE.LOADING -> LoadingUI()
                is LCE.CONTENT -> {
                    ContentUI(
                        state.data,
//                        allEmployee.value,
                        selectedGrid = selectedGrid,
                        selectedMenu = selectedMenu,
                        onDeleteClick = { id ->
                            try {

                                viewEmployee.getSingleEmployee(id)

                                viewEmployee.deleteEmployee(id)

                            } catch (exception: Exception) {
                                scope.launch { scaffoldState.snackbarHostState.showSnackbar("Delete failed") }
                                println(exception.message)

                            }
                        }
                    )
                }

                is LCE.ERROR -> ErrorUI(state.error)
                else -> {}
            }

//        ItemList(state = textState, viewEmployee)
        }
    }
}

@Composable
fun selectPreviewBtn(
    modifier: Modifier = Modifier,
    selectGrid: Boolean,
    selectMenu: Boolean,
    selectedGrid: () -> Unit,
    selectedMenu: () -> Unit
) {
    Row(
        modifier = modifier,
//            .fillMaxWidth()
//            .padding(
//                horizontal = 16.dp,
//                vertical = 16.dp
//            ), // this can change for contentPadding = PaddingValues(horizontal = 16.dp)
        verticalAlignment = Alignment.CenterVertically, // this modifier for column
//            horizontalArrangement = Arrangement.Center      // this can change for Arrangement.spacedBy()

    ) {
        val selectedContentColor: Color = MaterialTheme.colors.primary
        val unselectedContentColor: Color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
        val ripple = rememberRipple(
            bounded = true,
            color = selectedContentColor
        )


        IconButton(onClick = {

            selectedGrid()
        }) {
            Icon(
//                imageVector = Icons.Outlined.Menu,
                painter = painterResource("drawables/grid.svg"),
                modifier = Modifier.size(24.dp),
                contentDescription = null,
                tint = if (selectGrid) selectedContentColor else unselectedContentColor,
            )

        }

        IconButton(onClick = {
            selectedMenu()

        }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (selectMenu) selectedContentColor else unselectedContentColor,


                )

        }
    }
}

//@Composable
//fun DepartmentSearchView(departmentName : String, departments: List<com.hrappv.data.models.Department>){
//
//    MenuDropDown(name = departmentName, departments = departments)
//
//
//}

@Composable
private fun SearchView(
    modifier: Modifier = Modifier, state: MutableState<TextFieldValue>,
    departments: List<com.hrappv.data.models.Department>,
    name: String,
    onpressEnterSearch: () -> Unit,
    menuItemSelected : (com.hrappv.data.models.Department?) -> Unit,
    onCloseSearch: () -> Unit,
) {
    Row(
        modifier = modifier
//                    .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ), // this can change for contentPadding = PaddingValues(horizontal = 16.dp)
        verticalAlignment = Alignment.CenterVertically, // this modifier for column
//            horizontalArrangement = Arrangement.Center      // this can change for Arrangement.spacedBy()

    ) {
        OutlinedTextField(
            value = state.value,
            onValueChange = { value ->
                state.value = value
            },
            modifier = modifier.padding(end = 16.dp)
                .onKeyEvent {
                    if (it.key == Key.Enter) {
                        onpressEnterSearch()
                        true
                    } else {
                        false
                    }
                }.testTag("password"),
            textStyle = TextStyle(fontSize = 18.sp),
            placeholder = { Text("Search by name or ID") },
            label = { Text(text = "Search") },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            },
            trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            onCloseSearch()

                            state.value =
                                TextFieldValue("") // Remove text from TextField when you press the 'X' icon
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
            singleLine = true,

            )

        MenuDropDown(name = name, departments = departments ,menuItemSelected = menuItemSelected )


//        OutlinedTextField(
//            value = state.value,
//            onValueChange = { value ->
//                state.value = value
//            },
//            modifier = modifier.padding(end = 16.dp)
//                .onKeyEvent {
//                    if (it.key == Key.Enter) {
//                        onpressEnterSearch()
//                        true
//                    } else {
//                        false
//                    }
//                }.testTag("password"),
//            textStyle = TextStyle(fontSize = 18.sp),
//            placeholder = { Text("Department") },
//            label = { Text(text = "Department") },
//            leadingIcon = {
//                Icon(
//                    Icons.Default.Search,
//                    contentDescription = "",
//                    modifier = Modifier
//                        .padding(15.dp)
//                        .size(24.dp)
//                )
//            },
//            trailingIcon = {
//                if (state.value != TextFieldValue("")) {
//                    IconButton(
//                        onClick = {
//                            onCloseSearch()
//
//                            state.value =
//                                TextFieldValue("") // Remove text from TextField when you press the 'X' icon
//                        }
//                    ) {
//                        Icon(
//                            Icons.Default.Close,
//                            contentDescription = "",
//                            modifier = Modifier
//                                .padding(15.dp)
//                                .size(24.dp)
//                        )
//                    }
//                }
//            },
//            singleLine = true,
//
//            )

//        Button(onClick = {
////                    importState = LCE.LOADING
//
////                search(viewEmployee, empName)
//            onpressEnterSearch()
//        }) {
//            Icon(Icons.Outlined.Search, "Search")
//        }

    }

//    TextField(
//        value = state.value,
//        onValueChange = { value ->
//            state.value = value
//        },
//        modifier = modifier.padding(end = 16.dp),
//
//        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
//        leadingIcon = {
//            Icon(
//                Icons.Default.Search,
//                contentDescription = "",
//                modifier = Modifier
//                    .padding(15.dp)
//                    .size(24.dp)
//            )
//        },
//        trailingIcon = {
//            if (state.value != TextFieldValue("")) {
//                IconButton(
//                    onClick = {
//                        onCloseSearch()
//
//                        state.value =
//                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
//                    }
//                ) {
//                    Icon(
//                        Icons.Default.Close,
//                        contentDescription = "",
//                        modifier = Modifier
//                            .padding(15.dp)
//                            .size(24.dp)
//                    )
//                }
//            }
//        },
//        singleLine = true,
////        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
//        colors = TextFieldDefaults.textFieldColors(
//            textColor = Color.White,
//            cursorColor = Color.White,
//            leadingIconColor = Color.White,
//            trailingIconColor = Color.White,
//            backgroundColor = MaterialTheme.colors.primary,
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//            disabledIndicatorColor = Color.Transparent
//        )
//    )
}


//@Composable
//fun ItemList(state: MutableState<TextFieldValue>, viewEmployee: ViewEmpViewModel) {
//    val searchedText = state.value.text
//
//    //to do getAllEmployee()
//    val items by remember { mutableStateOf(listOf("Drink water", "Walk")) }//to do getAllEmployee()
//    val employees = viewEmployee.empResults.collectAsState()
//    val m = employees.value
////    val employees = viewEmployee.getEmployees(searchedText)
//
//    var filteredItems: List<String>
//    LazyColumn(modifier = Modifier.fillMaxWidth()) {
//
//        filteredItems = if (searchedText.isEmpty()) {
//            items
//        } else {
//            val resultList = ArrayList<String>()
//            for (item in items) {
//                if (item.lowercase(Locale.getDefault())
//                        .contains(searchedText.lowercase(Locale.getDefault()))
//                ) {
//                    resultList.add(item)
//                }
//            }
//            resultList
//        }
//        items(items = employees.value) { filteredItem ->
//            ItemListItem(
//                ItemText = filteredItem,
//                onItemClick = { /*Click event code needs to be implement*/
//                }
//            )
//        }
//
//    }
//}


//@Composable
//fun ItemListItem(ItemText: String, onItemClick: (String) -> Unit) {
//    Row(
//        modifier = Modifier
//            .clickable(onClick = { onItemClick(ItemText) })
////            .background(colorResource(id = R.color.purple_700))
//            .background(R.color.PictonBlue)
//            .height(57.dp)
//            .fillMaxWidth()
//            .padding(PaddingValues(8.dp, 16.dp))
//    ) {
//        Text(text = ItemText, fontSize = 18.sp, color = Color.White)
//    }
//}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentUI(
    data: List<GetEmployeeByName>,
    selectedGrid: Boolean,
    selectedMenu: Boolean,
    onDeleteClick: (id: Long) -> Unit,


    ) {

    if (selectedGrid)
        EmployeeGridLazyColumn(
            data, onDeleteClick
        )



    if (selectedMenu)
        EmployeeLazyColumn(data)


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmployeeGridLazyColumn(
    data: List<GetEmployeeByName>,
    onDeleteClick: (id: Long) -> Unit,

    ) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
//        .systemBarsPadding()
        ,
        columns = GridCells.Fixed(4),
        //columns = GridCells.Adaptive(minSize = 128.dp)
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(data, itemContent = { employee ->
            AnimatedVisibility(
                visible = true//!deletedPersonList.contains(person),
                , enter = expandVertically(),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 1000,
                    )
                )
            ) {
                EmployeeCardGrid(
                    Modifier.animateItemPlacement(
                        tween(durationMillis = 2000)
                    ),
                    employee, onDeleteClick
                )
            }


        })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmployeeLazyColumn(
    data: List<GetEmployeeByName>,
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
            items(items = data, itemContent = { employee ->
                EmployeeCardMenu(
                    Modifier.animateItemPlacement(
                        tween(durationMillis = 1000)
                    ),
                    employee,
                )


            })
        }


    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Card(
//        backgroundColor = Color.LightGray,
        modifier = Modifier.padding(horizontal = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Item(text = "ID", width = 55.dp)
            Item(text = "Name", width = 450.dp)
            Item(text = "Department", width = 200.dp)
            Item(text = "Attend Days", width = 200.dp)
            Item(text = "Absent Days", width = 200.dp)
            Item(text = "Total Part Time", width = 200.dp)
            Spacer(Modifier.weight(1f).fillMaxWidth())
        }
    }
}

@Composable
fun EmployeeCardGrid(
    modifier: Modifier = Modifier,
    employee: GetEmployeeByName,
    onDeleteClick: (id: Long) -> Unit,
) {
    Card(
        modifier = modifier.size(width = 150.dp, height = 350.dp)
            .padding(vertical = 4.dp, horizontal = 8.dp)

    ) {
        EmployeeItemGrid(employee, onDeleteClick)
    }

}

@Composable
@Preview
fun EmployeeCardMenu(
    modifier: Modifier = Modifier,
    employee: GetEmployeeByName,
) {
    Card(
//        backgroundColor = Color.LightGray,
//        .cardColors(containerColor = MaterialTheme.colors.primary)
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        EmployeeItemMenu(employee)
    }

}

@Composable
fun EmployeeItemGrid(employee: GetEmployeeByName, onDeleteClick: (id: Long) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }

//    var selectedItem by remember {
//        mutableStateOf(listItems[0])
//    }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row() {
            IconButton(onClick = {}){
                Icon(Icons.Outlined.Create , contentDescription = null)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                IconButton(onClick = {
                    expanded = true
                })
                {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    // menu item
                    DropdownMenuItem(onClick = {
                        onDeleteClick(employee.emp_id)
                        expanded = false
                    }) {
                        Text(text = "Delete")
                    }
                }

            }


        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource("drawables/ic_user_placeholder.png"),
                modifier = Modifier.size(100.dp)
                    .clip(CircleShape)
                    .border(
                        shape = CircleShape,
                        border = BorderStroke(2.dp, MaterialTheme.colors.onPrimary)
                    ),
                contentDescription = "User Logo",
//                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
            )
            Spacer(modifier = Modifier.width(8.dp))


        }
        Spacer(modifier = Modifier.height(8.dp))

        Card() { Text(text = employee.fname, style = MaterialTheme.typography.h6) }
        Spacer(modifier = Modifier.height(8.dp))

        Card() { Text(text = employee.department_name) }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ItemGrid(icon = FontAwesomeIcons.Solid.CashRegister, txt = employee.salary.toString())
            ItemGrid(icon = FontAwesomeIcons.Solid.CashRegister, txt = employee.vacanition.toString())
            ItemGrid(icon = FontAwesomeIcons.Solid.CashRegister, txt = employee.vbalance.toString())
            ItemGrid(icon = FontAwesomeIcons.Solid.CashRegister, txt = employee.bdl_balance.toString())


        }

    }


}

@Composable
private fun ItemGrid(modifier: Modifier = Modifier, icon: ImageVector, txt: String) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = txt , style = MaterialTheme.typography.body2)

    }

}

@Composable
@Preview
fun EmployeeItemMenu(employee: GetEmployeeByName) {


    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween //this can change for Arrangement.spacedBy()

    ) {
        Item(text = employee.emp_id.toString(), width = 55.dp)
        Item(text = employee.fname, width = 450.dp)
        Item(text = employee.department_name, width = 200.dp)
        Item(text = employee.totaldays.toString(), width = 200.dp)
        Item(text = employee.bith_day.toString(), width = 200.dp)
        Item(text = employee.salary.toString(), width = 200.dp)
        Item(text = employee.vacanition.toString(), width = 200.dp)
        Item(text = employee.vbalance.toString(), width = 200.dp)
        Item(text = employee.bdl_balance.toString(), width = 200.dp)

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
//        backgroundColor = Color.LightGray,
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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
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
private fun search(viewEmployee: ViewEmpViewModel, empName: String) {
    val scope = rememberCoroutineScope()

    scope.launch(Dispatchers.IO) {
        viewEmployee.getEmployees(empName)
    }
}
