package com.hrappv.ui.feature.view_employees

import com.hrappv.Employe
import com.hrappv.GetEmployeeByName
import com.hrappv.data.models.Employee
import com.hrappv.data.models.EmployeeResult
import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import utils.LCE
import javax.inject.Inject

class ViewEmpViewModel @Inject constructor(
    private val myRepo: MyRepo,
    // Inject your repos here...
) : ViewModel() {


    private val _EmpResults: MutableStateFlow<LCE<List<GetEmployeeByName>>> = MutableStateFlow(LCE.NOACTION)
    val empResults: StateFlow<LCE<List<GetEmployeeByName>>> = _EmpResults

    private val _isBackPressed = MutableStateFlow(false)
    val backToMain: StateFlow<Boolean> = _isBackPressed



    suspend fun getEmployees(name: String) {
        _EmpResults.value = LCE.LOADING
        val data = myRepo.viewEmployees.getEmployeeByName(name)
        if(data.isEmpty()){
            _EmpResults.value = LCE.ERROR("Employee Not Found")

        }else
        _EmpResults.value = LCE.CONTENT(data)
    }
    fun onBackPress() {
        _isBackPressed.value = true
    }
}