package com.hrappv.ui.feature.department.show_departments

import com.hrappv.data.models.Department
import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
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


    private val _isAddDepartmentPressed = MutableStateFlow(false)
    val iSAddDepartmentPressed: StateFlow<Boolean> = _isAddDepartmentPressed

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


    fun onAddDepartment() {
        _isAddDepartmentPressed.value = true
    }

    fun onBackPress() {
        _isBackPressed.value = true
        _isAddDepartmentPressed.value = false

    }

    fun closeDepart() {
        _isAddDepartmentPressed.value = false

    }

    fun setDepartments(departments: List<Department>) {
        _DepartResults.value = LCE.LOADING
        _DepartResults.value = LCE.CONTENT(departments)

    }

    fun setDepartError(msg: String) {
        _DepartResults.value = LCE.ERROR(msg)

    }

}