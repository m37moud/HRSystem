package com.hrappv.ui.feature.employe_result.emp_result_detail.details_days

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.hrappv.data.models.EmployeeResult
import com.hrappv.ui.feature.employe_result.emp_result_detail.details_days.DaysDetailViewModel

@Composable
fun DaysDetails(viewModel: DaysDetailViewModel, employeeResult:EmployeeResult){

    Column {
        Text(employeeResult.name)
        Text(employeeResult.department)
    }

}