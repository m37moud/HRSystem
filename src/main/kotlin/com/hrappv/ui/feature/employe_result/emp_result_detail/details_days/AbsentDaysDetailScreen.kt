package com.hrappv.ui.feature.employe_result.emp_result_detail.details_days

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hrappv.data.models.EmployeeResult
import com.toxicbakery.logging.Arbor
import util.Constatnts
import java.time.LocalDate

@Composable
fun AbsentDaysDetails(viewModel: AbsentDaysDetailViewModel, employeeResult: EmployeeResult) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(employeeResult.absentDays)
        val daysList = employeeResult.absentDays.split(",")
        Arbor.d(daysList.joinToString("\n"))
        daysList.forEach { day->
//            getAbsentDays(day)
//            Text(  getAbsentDays(day))


        }
    }


}
fun getAbsentDays(day : String) = LocalDate.ofEpochDay(day.toLong()).toString()
//{
////         var startDate = LocalDate.parse("$year-${Constatnts.getMonth((month.toInt() - 1).toString())}-21")
////         var endDate = LocalDate.parse("$year-${Constatnts.getMonth(month)}-20")
//
//    var days = LocalDate.ofEpochDay(day.toLong())
//}