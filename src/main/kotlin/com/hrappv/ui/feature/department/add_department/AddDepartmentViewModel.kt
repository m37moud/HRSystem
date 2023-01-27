package com.hrappv.ui.feature.department.add_department

import com.hrappv.data.models.Department
import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AddDepartmentViewModel @Inject constructor(
    private val myRepo: MyRepo,
//    private val dataSource : DepartmentDataSource
    // Inject your repos here...
) : ViewModel() {


    private val _insertEmp = MutableStateFlow("")
    val insertEmp: StateFlow<String> = _insertEmp


    private val _isBackPressed = MutableStateFlow(false)
    val backToMain: StateFlow<Boolean> = _isBackPressed


   suspend fun insertDepartmentFromImporter(department: List<Department>) {
            myRepo.department.insertMultiDepartment(department)
    }

    fun insertDepartment(department: Department) {
        launchOnIO {
            _insertEmp.value = myRepo.department.insertDepartment(department)
        }
    }

    fun dialogMessage(message: String = "") {
        _insertEmp.value = message
    }

    fun onBackPress() {
        _isBackPressed.value = true
    }
}