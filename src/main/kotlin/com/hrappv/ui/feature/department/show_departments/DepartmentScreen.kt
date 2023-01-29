package com.hrappv.ui.feature.department.show_departments

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrappv.data.models.Department
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import utils.LCE

@Composable
fun DepartmentScreen(viewModel: DepartmentViewModel) {
//    var path by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val departmentResult = viewModel.departResults.collectAsState().value
    val departments = viewModel.departments.collectAsState(null).value



    Surface(modifier = Modifier.fillMaxSize()) {

        Column(

            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
        ) {
//            Card(
//
////                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(16.dp),
//
//                elevation = 8.dp
//            ) {

            Box(
                modifier = Modifier.background(MaterialTheme.colors.surface)

                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            ) {


                Row(modifier = Modifier.align(Alignment.CenterEnd))
                {


                    Button(
                        onClick = {

                            scope.launch(Dispatchers.IO) {

                                viewModel.onAddDepartment()
                            }
                        },
                        modifier = Modifier.padding(horizontal = 16.dp),
                        // Provide a custom shape for this button. In this example. we specify the button to have
                        // round corners of 16dp radius.
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.elevation(5.dp),
                    ) {
                        Icon(imageVector = Icons.Outlined.AddCircle, contentDescription = "Add Department Screen")

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(text = "Add Department")
                    }


                }
            }

//            }


//            Spacer(modifier = Modifier.height(8.dp))

            scope.launch(Dispatchers.IO) {
                delay(1000)
                if (departments != null) {
                    if (departments.isNotEmpty()) {
                        viewModel.setDepartments(departments)
                    } else {
                        viewModel.setDepartError("No Departments is found")

                    }
                }

            }

            when (departmentResult) {

                is LCE.LOADING -> LoadingUI()

                is LCE.CONTENT -> {
                    ContentUI(
                        departmentResult.data,
//                        allEmployee.value,
                    ) {
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

                is LCE.ERROR -> ErrorUI(departmentResult.error)
                else -> {}
            }

        }
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchView(
    modifier: Modifier = Modifier,
    state: MutableState<TextFieldValue>,
    onpressEnterSearch: () -> Unit,
    onCloseSearch: () -> Unit,
) {
    Row(
        modifier = modifier
//                    .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
//                vertical = 16.dp
            ),
        // this can change for contentPadding = PaddingValues(horizontal = 16.dp)
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
            keyboardOptions = KeyboardOptions(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
//                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(16.dp),

            )


    }

}


@Composable
private fun ContentUI(
    data: List<Department>,
    onDeleteClick: (id: Long) -> Unit,


    ) {

    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val listState = rememberLazyListState()
//    Card(
//        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
//        shape = RoundedCornerShape(16.dp),
//        backgroundColor = MaterialTheme.colors.surface,
//        elevation = 8.dp,
//
//    ) {
    val showSearch by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 2
        }
    }
//    if(showSearch) {
    AnimatedVisibility(
        visible = !showSearch,
        enter = expandVertically(),
        exit = shrinkVertically(
            animationSpec = tween(
                durationMillis = 250,
            )
        )
    ) {
        Box(
            modifier = Modifier.background(MaterialTheme.colors.surface)
                .fillMaxWidth()

//            .border(
//                shape = RoundedCornerShape(16.dp),
//                border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.9f))
//            )
                .padding(start = 8.dp, end = 8.dp, bottom = 6.dp),

            ) {


            SearchView(modifier = Modifier.align(Alignment.CenterStart),
                state = textState, onpressEnterSearch = {
                }) {
                textState.value = TextFieldValue("")
            }
        }
    }
//        }

//    }

    val searchTxt = textState.value.text


    val filteredItems = if (searchTxt.isEmpty()) {
        data
    } else {
        val resultList = data.filter { department ->
            department.department.contains(searchTxt) || department.depart_id.toString().contains(searchTxt)

        }

        resultList
    }


    DepartmentLazyColumn(filteredItems, listState = listState, onDeleteClick)


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DepartmentLazyColumn(
    data: List<Department>,
    listState: LazyListState,
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
            modifier = Modifier.fillMaxSize(),
            state = listState
//                .padding(vertical = 4.dp) // this can change for contentPadding = PaddingValues(horizontal = 16.dp)
            , contentPadding = PaddingValues(vertical = 4.dp)
        ) {

            items(items = data, itemContent = { department ->
                AnimatedVisibility(
                    visible = true//!deletedPersonList.contains(person),
                    , enter = expandVertically(),
                    exit = shrinkVertically(
                        animationSpec = tween(
                            durationMillis = 1000,
                        )
                    )
//                    exit = fadeOut(
//                        animationSpec = TweenSpec(200, 200, FastOutLinearInEasing)
//                    )
                ) {

                    DepartmentCardMenu(
                        Modifier.animateItemPlacement(
                            animationSpec = tween(durationMillis = 250)
                        ),
                        department = department,
                    )
                }


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
private fun DepartmentCardMenu(
    modifier: Modifier = Modifier,
    department: Department,
) {
    Card(
//        backgroundColor = Color.LightGray,
//        .cardColors(containerColor = MaterialTheme.colors.primary)
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        DepartmentItemMenu(department)
    }

}

@Composable
@Preview
private fun DepartmentItemMenu(
    department: Department,
) {


    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween //this can change for Arrangement.spacedBy()

    ) {
        Item(text = department.depart_id.toString(), width = 55.dp)
        Item(text = department.department.toString(), width = 450.dp)
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


