package com.hrappv.ui.feature.add_department

import com.hrappv.data.models.Department
import com.hrappv.data.models.Employees
import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DepartmentViewModel @Inject constructor(
    private val myRepo: MyRepo,
    // Inject your repos here...
) : ViewModel() {

    private val _isBackPressed = MutableStateFlow(false)
    val backToMain: StateFlow<Boolean> = _isBackPressed


    fun insertDepartmentFromImporter(department :List<Department>){
        launchOnIO {
            myRepo.department.insertMultiDepartment(department)
        }
    }


    fun onBackPress() {
        _isBackPressed.value = true
    }
}