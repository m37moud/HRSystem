import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrappv.GetEmployeeByName
import com.hrappv.ui.feature.view_employees.ViewEmpViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import utils.LCE

//https://johncodeos.com/how-to-add-search-in-list-with-jetpack-compose/
@Composable
 fun ViewEmpScreen(viewEmployee: ViewEmpViewModel) {
    val scope = rememberCoroutineScope()
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    val empName = textState.value.text

    val employees = viewEmployee.empResults.collectAsState()



    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ), // this can change for contentPadding = PaddingValues(horizontal = 16.dp)
            verticalAlignment = Alignment.CenterVertically, // this modifier for column
            horizontalArrangement = Arrangement.Center      // this can change for Arrangement.spacedBy()

        ) {
            SearchView(textState)

            Button(onClick = {
//                    importState = LCE.LOADING

                scope.launch(Dispatchers.IO) {
                    viewEmployee.getEmployees(empName)
//                        scaffoldState.snackbarHostState.showSnackbar("Button Clicked ")
//                        importState = repository.getEmployReport(path)
                }
            }) {
                Icon(Icons.Outlined.Search, "Search")
            }


        }

        when (val state = employees.value) {
            is LCE.LOADING -> LoadingUI()
            is LCE.CONTENT -> ContentUI(state.data)
            is LCE.ERROR -> ErrorUI(state.error)
            else -> {}
        }

//        ItemList(state = textState, viewEmployee)
    }
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
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
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
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

@Composable
@Preview
fun ContentUI(data: List<GetEmployeeByName>) {

    Column(
        modifier = Modifier.fillMaxSize()
//            .horizontalScroll(rememberScrollState())
        ,
        horizontalAlignment = Alignment.CenterHorizontally // this can change for verticalAlignment
    ) {
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


        LazyColumn(
            modifier = Modifier.fillMaxSize()
//                .padding(vertical = 4.dp) // this can change for contentPadding = PaddingValues(horizontal = 16.dp)
            , contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(items = data) { employee ->
                EmployeeCard(employee)

            }
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
    }

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
