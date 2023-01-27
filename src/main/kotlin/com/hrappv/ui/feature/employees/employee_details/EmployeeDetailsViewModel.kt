package com.hrappv.ui.feature.employees.employee_details

import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class EmployeeDetailsViewModel @Inject constructor(
    private val myRepo: MyRepo
) : ViewModel() {

    private val _isBackPressed = MutableStateFlow(false)
    val back: StateFlow<Boolean> = _isBackPressed


    fun onBackPress() {
        _isBackPressed.value = true
    }
}