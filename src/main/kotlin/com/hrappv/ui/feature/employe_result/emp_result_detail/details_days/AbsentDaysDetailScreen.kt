package com.hrappv.ui.feature.employe_result.emp_result_detail.details_days

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hrappv.data.models.EmployeeResult
import com.hrappv.data.util.AbsentType
import com.hrappv.ui.components.TextFieldMenu
import com.toxicbakery.logging.Arbor
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import util.Constatnts
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AbsentDaysDetails(viewModel: AbsentDaysDetailViewModel, employeeResult: EmployeeResult) {


    Column(modifier = Modifier.fillMaxSize()) {
//        Text(employeeResult.absentDays)
//        val daysList = employeeResult.absentDays.split(",")
        val daysList = employeeResult.absentDays
        Arbor.d(daysList.joinToString("\n"))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = daysList, key = { it }, itemContent = { day ->
                    RowDay(
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(
                                durationMillis = 1000,
//                            easing = LinearOutSlowInEasing,
                            )
                        ), date = day.absnt_date!!
//                        , month = employeeResult.month, year = employeeResult.year
                    )


            }
            )
        }
    }


}

@Composable
fun RowDay(modifier: Modifier = Modifier, date: LocalDate) {
//    val date = getAbsentDays(day, month, year).toKotlinLocalDate().toJavaLocalDate()
    var check by remember { mutableStateOf(false) }
    val pattern = DateTimeFormatter.ofPattern("dd.MMM.yyyy")
    val formatDate = date.format(pattern)

    Card(elevation = 10.dp, shape = RoundedCornerShape(16.dp)) {
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
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {}) {
                Icon(Icons.Outlined.AccountCircle, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(8.dp))

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
