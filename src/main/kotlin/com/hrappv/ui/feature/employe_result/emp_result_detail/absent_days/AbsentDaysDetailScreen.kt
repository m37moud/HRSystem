package com.hrappv.ui.feature.employe_result.emp_result_detail.absent_days

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hrappv.data.models.AbsentDay
import com.hrappv.data.models.EmployeeResult
import com.hrappv.data.util.AbsentType
import com.hrappv.ui.components.ErrorUI
import com.hrappv.ui.components.LoadingScreen
import com.hrappv.ui.components.TextFieldMenu
import com.toxicbakery.logging.Arbor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import utils.LCE
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AbsentDaysDetails(viewModel: AbsentDaysDetailViewModel, employeeResult: EmployeeResult) {
    val scope = rememberCoroutineScope()
    val absentList = viewModel.absentDays.collectAsState().value

    scope.launch(Dispatchers.IO) {

        viewModel.setAbsentList(employeeResult.name, employeeResult.month, employeeResult.year)
//        if(absentList !=null){
//
//        }
    }
    when (val state = absentList) {
        is LCE.LOADING -> LoadingScreen()
        is LCE.CONTENT -> Content(state.data)
        is LCE.ERROR -> ErrorUI(state.error)
        else -> {}
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(AbsentDayList: List<AbsentDay>) {
    Column(modifier = Modifier.fillMaxSize()) {
//        Text(employeeResult.absentDays)
//        val daysList = employeeResult.absentDays.split(",")
//        val daysList = employeeResult.absentDays
        Arbor.d(AbsentDayList.joinToString("\n"))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = AbsentDayList, key = { it }, itemContent = { day ->
                RowDay(
                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(
                            durationMillis = 1000,
//                            easing = LinearOutSlowInEasing,
                        )
                    ), absentDay = day!!
//                        , month = employeeResult.month, year = employeeResult.year
                )


            }
            )
        }
    }
}

@Composable
fun RowDay(modifier: Modifier = Modifier, absentDay: AbsentDay) {
//    val date = getAbsentDays(day, month, year).toKotlinLocalDate().toJavaLocalDate()
    var check by remember { mutableStateOf(false) }
    val pattern = DateTimeFormatter.ofPattern("dd.MMM.yyyy")
    val formatDate = absentDay.absnt_date!!.format(pattern)
    var click by remember { mutableStateOf(false) }
    var absentNote by remember { mutableStateOf("") }

    Card(modifier = modifier, elevation = 10.dp, shape = RoundedCornerShape(16.dp)) {
        Column {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Checkbox(checked = check, enabled = true, onCheckedChange = {
                    check = it
                })
                Spacer(modifier = Modifier.width(8.dp))
//            Text(formatDate)
                Item(formatDate)
                Spacer(modifier = Modifier.width(8.dp))
                val lis = AbsentType.values().toList()
                TextFieldMenu(name = "Type", listItems = lis, menuItemSelected = {
                    println(it)
                })
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { click = !click }) {
                    val text = if (absentDay.absnt_reason!!.isEmpty()){
                        "Absent Reason"
                    }else
                        absentDay.absnt_reason
                    Text(text!!)
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {}) {
                    Icon(Icons.Outlined.AccountCircle, contentDescription = null)
                }
                Spacer(modifier = Modifier.width(8.dp))

            }

            if (click) {
                BasicTextField(
                    value = absentNote,
                    onValueChange = {
                        absentNote = it
                    },
                    modifier = Modifier.fillMaxWidth().height(150.dp).clip(RectangleShape)
                        .border(
                            shape = RectangleShape,
                            border = BorderStroke(2.dp, MaterialTheme.colors.onPrimary)
                        ),
                    textStyle = MaterialTheme.typography.body1,

                    )
            }

        }

    }

}

fun getAbsentDays(day: String = "1", month: String = "1", year: String = "1990"): LocalDate =
    LocalDate.of(year.toInt(), month.toInt(), day.toInt())

@Composable
fun Item(text: String, width: Dp = 200.dp) {
    val modifierText = Modifier.padding(5.dp).height(35.dp)

    Card(
        elevation = 10.dp,
//        backgroundColor = Color.LightGray,
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Box {
            Text(
                maxLines = 1,
                textAlign = TextAlign.Center,
                text = text,

                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.ExtraBold),
                modifier = modifierText.width(width).align(Alignment.Center),
            )
        }

    }
}
