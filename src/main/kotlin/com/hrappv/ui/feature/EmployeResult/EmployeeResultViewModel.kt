package com.hrappv.ui.feature.EmployeResult

import com.hrappv.data.models.CamRegisterDay
import com.hrappv.data.models.Employee
import com.hrappv.data.models.EmployeeResult
import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import utils.LCE
import javax.inject.Inject

class EmployeeResultViewModel @Inject constructor(
    private val myRepo: MyRepo,
    // Inject your repos here...
) : ViewModel() {
    companion object {
        const val INIT_FOLDER_PATH = ""
    }

    private val _folderPath = MutableStateFlow(INIT_FOLDER_PATH)
    val folderPath: StateFlow<String> = _folderPath

    private val _employee: MutableStateFlow<List<CamRegisterDay?>> = MutableStateFlow(emptyList())
    val employee: StateFlow<List<CamRegisterDay?>> = _employee

    private val _EmpResults: MutableStateFlow<LCE<List<EmployeeResult>>> = MutableStateFlow(LCE.NOACTION)
    val empResults: StateFlow<LCE<List<EmployeeResult>>> = _EmpResults




    private val _isBackPressed = MutableStateFlow(false)
    val backToMain: StateFlow<Boolean> = _isBackPressed

    fun onClickMeClicked() {
        _folderPath.value = myRepo.getClickedWelcomeText()
    }

    suspend fun getEmployeeResults(folderPath: String) {
        _EmpResults.value = LCE.LOADING
        _EmpResults.value = myRepo.importer.getEmployReport(folderPath)
    }


    //TODO insert all exel in database
    suspend fun registerDayByCam(folderPath: String? = null , pList: List<String>? = null) {

        _employee.value = myRepo.importer.getAllEmployeeInfo(folderPath,pList)

    }
    fun onBackPress() {
        _isBackPressed.value = true
    }

}