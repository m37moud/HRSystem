package com.hrappv.ui.feature.employees.add_Employee

import com.hrappv.data.models.EmployeeResult
import com.hrappv.data.models.Employees
import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import utils.LCE
import javax.inject.Inject


class AddEmployeViewModel @Inject constructor(
    private val myRepo: MyRepo,
    // Inject your repos here...
) : ViewModel() {
    companion object {
        const val INIT_FOLDER_PATH = ""
    }

    private val _folderPath = MutableStateFlow(INIT_FOLDER_PATH)
    val folderPath: StateFlow<String> = _folderPath


    private val _EmpResults: MutableStateFlow<LCE<List<EmployeeResult>>> = MutableStateFlow(LCE.NOACTION)
    val empResults: StateFlow<LCE<List<EmployeeResult>>> = _EmpResults



    private val _insertEmp = MutableStateFlow("")
    val insertEmp: StateFlow<String> = _insertEmp



    private val _isMultiInsertFinish = MutableStateFlow(false)
    val isMultiInsertFinish: StateFlow<Boolean> = _isMultiInsertFinish


    private val _isBackPressed = MutableStateFlow(false)
    val backToMain: StateFlow<Boolean> = _isBackPressed

    fun onClickMeClicked() {
        _folderPath.value = myRepo.getClickedWelcomeText()
    }

    suspend fun getEmployeResults(folderPath: String) {
        _EmpResults.value = LCE.LOADING
        _EmpResults.value = myRepo.importer.getEmployReport(folderPath)
    }

    suspend fun insertEmpFromImporter(emplist :List<Employees>){
//        launchOnIO {
        _isMultiInsertFinish.value =   myRepo.viewEmployees.insertMultiEmployee(emplist)
//        }
    }

    fun insertNewEmp(employee : Employees){
        launchOnIO {
            _insertEmp.value = myRepo.viewEmployees.insertEmployee(employee)
        }
    }

    fun dialogMessage(message: String = "" ){
        _insertEmp.value = message
    }

    val departments = myRepo.department.getAllDepartments()

    fun onBackPress() {
        _isBackPressed.value = true
    }

}