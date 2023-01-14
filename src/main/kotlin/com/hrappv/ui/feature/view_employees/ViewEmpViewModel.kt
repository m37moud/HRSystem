package com.hrappv.ui.feature.view_employees

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.hrappv.Employe
import com.hrappv.GetAllEmployees
import com.hrappv.GetEmployeeByName
import com.hrappv.data.models.Employee
import com.hrappv.data.models.EmployeeResult
import com.hrappv.data.repo.MyRepo
import com.hrappv.ui.feature.main.MainViewModel
import com.hrappv.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import utils.LCE
import javax.inject.Inject

class ViewEmpViewModel @Inject constructor(
    private val myRepo: MyRepo,
    // Inject your repos here...
) : ViewModel() {

    private val _EmpResults: MutableStateFlow<LCE<List<GetEmployeeByName>>> = MutableStateFlow(LCE.NOACTION)
    val empResults: StateFlow<LCE<List<GetEmployeeByName>>> = _EmpResults


    private val _queries = MutableStateFlow("")
    val queries: StateFlow<String> = _queries

    private val _allEmps: MutableStateFlow<LCE<List<GetAllEmployees>>> = MutableStateFlow(LCE.NOACTION)
    val allEmployees: StateFlow<LCE<List<GetAllEmployees>>> = _allEmps


    private val _isBackPressed = MutableStateFlow(false)
    val backToMain: StateFlow<Boolean> = _isBackPressed



     fun getEmployees(name: String) {
        launchOnIO{
            _EmpResults.value = LCE.LOADING
        var n = ""
         queries.collect{
            n = it
            println(it.toString())
//            myRepo.viewEmployees.getEmployeeByName("%$n%")
            val data = myRepo.viewEmployees.getEmployeeByName("%$n%")
            println(data.toString())

            if (data.isEmpty()) {
                _EmpResults.value = LCE.ERROR("Employee Not Found")

            } else
                _EmpResults.value = LCE.CONTENT(data)
        }

//        println(data.toString())
        }
    }

    fun getAllEmployees() {
        val data = myRepo.viewEmployees.getAllEmployees()
        println(data.toString())
        _allEmps.value = LCE.CONTENT(data)
    }

    fun getQueries(queries : String){
        _queries.value = queries
    }

    fun onBackPress() {
        _isBackPressed.value = true
    }
}