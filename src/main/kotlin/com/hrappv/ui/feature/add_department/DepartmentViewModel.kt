package com.hrappv.ui.feature.add_department

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.hrappv.GetEmployeeByName
import com.hrappv.data.local.datastore.DepartmentDataSource
import com.hrappv.data.models.Department
import com.hrappv.data.models.Employees
import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import utils.LCE
import javax.inject.Inject

class DepartmentViewModel @Inject constructor(
    private val myRepo: MyRepo,
//    private val dataSource : DepartmentDataSource
    // Inject your repos here...
) : ViewModel() {

    private val _DepartResults: MutableStateFlow<LCE<List<Department>>> = MutableStateFlow(LCE.LOADING)
    val departResults: StateFlow<LCE<List<Department>>> = _DepartResults

    val departments = myRepo.department.getAllDepartments()


    val departNumber = MutableStateFlow(0)


    private val _ISAddDepartmentPressed = MutableStateFlow(false)
    val iSAddDepartmentPressed: StateFlow<Boolean> = _ISAddDepartmentPressed

    private val _isBackPressed = MutableStateFlow(false)
    val backToMain: StateFlow<Boolean> = _isBackPressed

//    fun getAllDepartment() {
//        launchOnIO {
//            _DepartResults.value = LCE.LOADING
//                val data = myRepo.department.getAllDepartments().collectAsState(emptyList()).value
//                departNumber.value = data.size
//
//                if (data.isEmpty()) {
//                    _DepartResults.value = LCE.ERROR("Employee Not Found")
//
//                } else
//                    _DepartResults.value = LCE.CONTENT(data)
//
//        }
//    }


    fun insertDepartmentFromImporter(department: List<Department>) {
        launchOnIO {
            myRepo.department.insertMultiDepartment(department)
        }
    }


    fun insertDepartment(department: Department) {
        launchOnIO {
            myRepo.department.insertDepartment(department)
        }
    }


    fun onAddDepartment() {
        _ISAddDepartmentPressed.value = true
    }
    fun onBackPress() {
        _ISAddDepartmentPressed.value = false
        _isBackPressed.value = true
    }

    fun setDepartments(departments: List<Department>) {
        _DepartResults.value = LCE.LOADING

        if (departments.isEmpty()) {
            _DepartResults.value = LCE.ERROR("No Department is Found")

        } else _DepartResults.value = LCE.CONTENT(departments)
//


    }
}