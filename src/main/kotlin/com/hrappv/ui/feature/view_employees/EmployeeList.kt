@file:OptIn(ExperimentalComposeUiApi::class)

import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrappv.GetEmployeeByName
import com.hrappv.ui.feature.view_employees.ViewEmpViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import utils.LCE

//https://johncodeos.com/how-to-add-search-in-list-with-jetpack-compose/
@Composable
fun ViewEmpScreen(viewEmployee: ViewEmpViewModel) {
    val scope = rememberCoroutineScope()
    val textState = remember { mutableStateOf(TextFieldValue("")) }


    val empName = textState.value.text
    viewEmployee.getQueries(empName)
    val employees = viewEmployee.empResults.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    ).align(Alignment.CenterStart), // this can change for contentPadding = PaddingValues(horizontal = 16.dp)
                verticalAlignment = Alignment.CenterVertically, // this modifier for column
//            horizontalArrangement = Arrangement.Center      // this can change for Arrangement.spacedBy()

            ) {
                SearchView(state = textState, onpressEnterSearch = {
//                search(viewEmployee, empName)
                    scope.launch(Dispatchers.IO) {
                        viewEmployee.getEmployees(empName)
                    }
                }) {
//                search(viewEmployee, "")
                    scope.launch(Dispatchers.IO) {
                        viewEmployee.getEmployees("")
                    }
                }

                Button(onClick = {
//                    importState = LCE.LOADING

//                search(viewEmployee, empName)
                    scope.launch(Dispatchers.IO) {
                        viewEmployee.getEmployees(empName)
                    }
                }) {
                    Icon(Icons.Outlined.Search, "Search")
                }


            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    ).align(Alignment.CenterEnd), // this can change for contentPadding = PaddingValues(horizontal = 16.dp)
                verticalAlignment = Alignment.CenterVertically, // this modifier for column
//            horizontalArrangement = Arrangement.Center      // this can change for Arrangement.spacedBy()

            ){
                val selectedContentColor: Color = MaterialTheme.colors.primary
                val unselectedContentColor: Color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                val ripple = rememberRipple(
                    bounded = true,
                    color = selectedContentColor
                )

                IconButton(modifier = Modifier ,onClick = {}){
                    Icon(imageVector = Icons.Default.Menu,
                    contentDescription = null,
                        )

                }

                IconButton(onClick = {}){
                    Icon(imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )

                }
            }

        }



        when (val state = employees.value) {
            is LCE.LOADING -> LoadingUI()
            is LCE.CONTENT -> {
                ContentUI(state.data)
            }
            is LCE.ERROR -> ErrorUI(state.error)
            else -> {}
        }

//        ItemList(state = textState, viewEmployee)
    }
}

@Composable
fun SearchView(
    modifier: Modifier = Modifier, state: MutableState<TextFieldValue>,
    onpressEnterSearch: () -> Unit,
    onCloseSearch: () -> Unit,
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
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        placeholder = { Text("put url excel folder") },
        label = { Text(text = "Search by name or ID") },
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
@Preview
fun ContentUI(data: List<GetEmployeeByName>) {

    Column(
        modifier = Modifier.fillMaxSize()
//            .horizontalScroll(rememberScrollState())
        ,
        horizontalAlignment = Alignment.CenterHorizontally // this can change for verticalAlignment
    ) {
        Header()

        /*
        
        LazyVerticalGrid(
    modifier = Modifier
        .fillMaxWidth()
        .systemBarsPadding(),
    columns = GridCells.Fixed(7),
    //columns = GridCells.Adaptive(minSize = 128.dp)
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
) {
    items(dates) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = "${it + 1}")
        }
    }
}


// another 
val dates = MutableList(35) { if (it + 1 > 31) -1 else it + 1 }
val chunks = dates.chunked(7)

LazyColumn(
    Modifier
        .fillMaxSize()
        .systemBarsPadding()
) {
    items(chunks) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            it.forEach { date ->
                Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                    if (date != -1) Text(text = "$date")
                }
            }
        }
    }
}
        */


        LazyColumn(
            modifier = Modifier.fillMaxSize()
//                .padding(vertical = 4.dp) // this can change for contentPadding = PaddingValues(horizontal = 16.dp)
            , contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(items = data) { employee ->
                Row(
                    Modifier.animateItemPlacement(
                        tween(durationMillis = 250)
                    )
                ) {
                    EmployeeCard(employee)

                }

            }
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
@Preview
fun EmployeeCard(employee: GetEmployeeByName) {
    Card(
        backgroundColor =
        Color.LightGray,
//        .cardColors(containerColor = MaterialTheme.colors.primary)
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        EmployeeItem(employee)
        EmployeeItem2(employee)
    }

}

@Composable
fun EmployeeItem2(employee: GetEmployeeByName) {

}

@Composable
@Preview
fun EmployeeItem(employee: GetEmployeeByName) {


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
        backgroundColor =
        Color.LightGray,
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
private fun search(viewEmployee: ViewEmpViewModel, empName: String) {
    val scope = rememberCoroutineScope()

    scope.launch(Dispatchers.IO) {
        viewEmployee.getEmployees(empName)
    }
}
