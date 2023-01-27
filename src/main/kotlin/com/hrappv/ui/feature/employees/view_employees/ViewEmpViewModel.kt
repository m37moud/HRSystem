package com.hrappv.ui.feature.employees.view_employees

import com.hrappv.GetAllEmployees
import com.hrappv.GetEmployeeByID
import com.hrappv.GetEmployeeByName
import com.hrappv.data.local.datastore.ViewEmpDataSource
import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import utils.LCE
import javax.inject.Inject

class ViewEmpViewModel @Inject constructor(
    private val myRepo: MyRepo,
    private val dataSource: ViewEmpDataSource
    // Inject your repos here...
) : ViewModel() {


    val departments = myRepo.department.getAllDepartments()


    private val _EmpResults: MutableStateFlow<LCE<List<GetEmployeeByName>>> = MutableStateFlow(LCE.LOADING)
    val empResults: StateFlow<LCE<List<GetEmployeeByName>>> = _EmpResults

    private val _employee: MutableStateFlow<GetEmployeeByID> =
        MutableStateFlow(GetEmployeeByID(1, "", "", 1, "", 0.0f, 1, 1, 1))
    val employee: StateFlow<GetEmployeeByID> = _employee


    private val _queries = MutableStateFlow("")
    val queries: StateFlow<String> = _queries


    private val _delete = MutableStateFlow(false)
    val delete: StateFlow<Boolean> = _delete

    private val _allEmps: MutableStateFlow<LCE<List<GetAllEmployees>>> = MutableStateFlow(LCE.LOADING)
    val allEmps: StateFlow<LCE<List<GetAllEmployees>>> = _allEmps

    val allEmployees = myRepo.viewEmployees.getAllEmployees()

    val empNumber = MutableStateFlow(0)


    private val _empDetailsPressed = MutableStateFlow<GetAllEmployees>(GetAllEmployees(0, "", "", 0, "", 0f, 0, 0, 0))
    val empDetailsPressed: StateFlow<GetAllEmployees> = _empDetailsPressed

    private val _isAddEmployeePressed = MutableStateFlow(false)
    val isAddEmployeePressed: StateFlow<Boolean> = _isAddEmployeePressed

    private val _isEmpDetailsPressed = MutableStateFlow(false)
    val isEmpDetailsPressed: StateFlow<Boolean> = _isEmpDetailsPressed

    private val _isBackPressed = MutableStateFlow(false)
    val backToMain: StateFlow<Boolean> = _isBackPressed

    init {

    }

    fun getEmployees(name: String) {
        launchOnIO {
            _EmpResults.value = LCE.LOADING
            var n = ""
            queries.collect {
                n = it
                println(it.toString())
//            myRepo.viewEmployees.getEmployeeByName("%$n%")
                val data = dataSource.getEmployeeByName("%$n%")
                empNumber.value = data.size
                println(data.toString())

                if (data.isEmpty()) {
                    _EmpResults.value = LCE.ERROR("Employee Not Found")

                } else
                    _EmpResults.value = LCE.CONTENT(data)


            }
////            val data = myRepo.viewEmployees.getEmployeeByName("%$name%")
//            val data = dataSource.getEmployeeByName("%$name%")
////            println(data.toString())
//
//            if (data.isEmpty()) {
//                _EmpResults.value = LCE.ERROR("Employee Not Found")
//
//            } else
//                _EmpResults.value = LCE.CONTENT(data)

//        println(data.toString())
        }
    }


    fun getEmployeesByDepartment(id: Long) {
        launchOnIO {
            val data = dataSource.getEmployeeByDepartment(id)
            println(data.joinToString(" - "))

        }

    }


    fun deleteEmployee(id: Long) {
        println("delete press")
        launchOnIO {

//         myRepo.viewEmployees.deleteEmployee(id)
            val result = dataSource.deleteEmployee(id)
            _delete.emit(result)
        }

    }

    fun getSingleEmployee(id: Long) {
        launchOnIO {
//            val result = myRepo.viewEmployees.getEmployeeByID(id)
            val result = dataSource.getEmployeeByID(id)
            println(result.toString())
            if (result != null) {
                _employee.value = result
            }
        }
    }

    fun setEmpList(employees: List<GetAllEmployees>) {
        _allEmps.value = LCE.LOADING
        _allEmps.value = LCE.CONTENT(employees)

    }

    fun setEmpError(msg: String) {
        _allEmps.value = LCE.ERROR(msg)

    }

    fun getQueries(queries: String) {
        _queries.value = queries
    }

    fun onStartEmpDetails(emp: GetAllEmployees) {
        _isEmpDetailsPressed.value = true
        _empDetailsPressed.value = emp
    }

    fun onAddEmployee() {
        _isAddEmployeePressed.value = true
    }

    fun onBackPress() {
        _isBackPressed.value = true
    }
}