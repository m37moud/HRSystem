package com.hrappv.ui.feature.employe_result.emp_result_detail.absent_days

import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import javax.inject.Inject

class DaysDetailViewModel @Inject constructor(
    private val myRepo: MyRepo,
//    private val dataSource : DepartmentDataSource
    // Inject your repos here...
) : ViewModel() {
}