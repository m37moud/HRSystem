package com.hrappv.ui.feature.employe_result.emp_result_detail.details_days

import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import javax.inject.Inject

class AbsentDaysDetailViewModel @Inject constructor(
    private val myRepo: MyRepo,
//    private val dataSource : DepartmentDataSource
    // Inject your repos here...
) : ViewModel() {
}