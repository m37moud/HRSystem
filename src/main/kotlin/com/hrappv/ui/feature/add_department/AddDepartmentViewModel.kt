package com.hrappv.ui.feature.add_department

import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AddDepartmentViewModel@Inject constructor(
    private val myRepo: MyRepo,
//    private val dataSource : DepartmentDataSource
    // Inject your repos here...
) : ViewModel()  {
    private val _isBackPressed = MutableStateFlow(false)
    val backToMain: StateFlow<Boolean> = _isBackPressed
    fun onBackPress() {
        _isBackPressed.value = true
    }
}