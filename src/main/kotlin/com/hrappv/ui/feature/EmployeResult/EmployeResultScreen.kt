package com.hrappv.ui.feature.EmployeResult

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hrappv.data.di.module.EmployeeResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import utils.LCE

@Composable
fun EmployeeResultScreen(
    viewModel: EmployeeResultViewModel
//                         ,mainViewModel: MainViewModel
) {

    var path by remember { mutableStateOf("E:\\8") }
    // 1
//    var importState by remember { mutableStateOf<LCE<List<EmployeeResult>>?>(null) }
    val importState = viewModel.empResults.collectAsState()

    // 2
    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState, topBar = {
            TopAppBar(
                title = {
                    Text(text = "Register Attends")
                }, navigationIcon = {
                    IconButton(onClick = { viewModel.onBackPress() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                }
            )
        }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {// pest padding dp is 8.dp and //modifier.verticalScroll(rememberScrollState())


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

                OutlinedTextField(
                    value = path,
                    onValueChange = { path = it },
                    modifier = Modifier.padding(end = 16.dp).weight(1f),
                    placeholder = { Text("put url excel folder") },
                    label = { Text(text = "paste here ...") },
                    leadingIcon = { Icon(Icons.Filled.Refresh, "search location") }

                )

                Button(onClick = {
//                    importState = LCE.LOADING

                    scope.launch(Dispatchers.IO) {
                        viewModel.getEmployeResults(path)
//                        scaffoldState.snackbarHostState.showSnackbar("Button Clicked ")
//                        importState = repository.getEmployReport(path)
                    }
                }) {
                    Icon(Icons.Outlined.Search, "Search")
                }
            }


            when (val state = importState.value) {
                is LCE.LOADING -> LoadingUI()
                is LCE.CONTENT -> ContentUI(state.data)
                is LCE.ERROR -> ErrorUI(state.error)
            }
        }
    }


}

@Composable
@Preview
fun ContentUI(data: List<EmployeeResult>) {

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
fun EmployeeCard(employeeResult: EmployeeResult) {
    Card(
        backgroundColor =
        Color.LightGray,
//        .cardColors(containerColor = MaterialTheme.colors.primary)
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        EmployeeItem(employeeResult)
    }

}

@Composable
@Preview
fun EmployeeItem(employeeResult: EmployeeResult) {


    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween //this can change for Arrangement.spacedBy()

    ) {
        Item(text = employeeResult.id, width = 55.dp)
        Item(text = employeeResult.name, width = 450.dp)
        Item(text = employeeResult.department, width = 200.dp)
        Item(text = employeeResult.numberOfAttendDays.toString(), width = 200.dp)
        Item(text = employeeResult.numberOfAbsentDays.toString(), width = 200.dp)
        Item(text = employeeResult.totalPartTime.toString(), width = 200.dp)

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