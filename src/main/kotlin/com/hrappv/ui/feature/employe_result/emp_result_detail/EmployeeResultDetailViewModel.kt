package com.hrappv.ui.feature.employe_result.emp_result_detail

import com.hrappv.GetAllEmployees
import com.hrappv.data.models.EmployeeResult
import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class EmployeeResultDetailViewModel @Inject constructor(
    private val myRepo: MyRepo,
//    private val dataSource : DepartmentDataSource
    // Inject your repos here...
) : ViewModel() {


    private val _isBackPressed = MutableStateFlow(false)
    val back: StateFlow<Boolean> = _isBackPressed

    fun onBackPress() {
        _isBackPressed.value = true
    }
}