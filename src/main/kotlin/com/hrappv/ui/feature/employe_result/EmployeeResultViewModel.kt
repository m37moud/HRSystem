package com.hrappv.ui.feature.employe_result

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.hrappv.data.models.CamRegisterDay
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

    private val _empResults: MutableStateFlow<LCE<List<EmployeeResult>>> = MutableStateFlow(LCE.NOACTION)
    val empResults: StateFlow<LCE<List<EmployeeResult>>> = _empResults

 var insert by mutableStateOf(false)
    private set
 var message by mutableStateOf("")
    private set
 var result by mutableStateOf("")
    private set


    private val _isBackPressed = MutableStateFlow(false)
    val backToMain: StateFlow<Boolean> = _isBackPressed

    fun onClickMeClicked() {
        _folderPath.value = myRepo.getClickedWelcomeText()
    }

    suspend fun getEmployeeResults(folderPath: String) {
        _empResults.value = LCE.LOADING
//        _empResults.value = myRepo.importer.getEmployReport(folderPath)
    }


    /* TODO insert all exel in database */
     fun registerDayByCam(folderPath: String? = null , pList: List<String>? = null) {
    val camRegisterDay = myRepo.importer.getAllEmployeeInfo(folderPath,pList)
        if (camRegisterDay.isNotEmpty()){
            message = "import successful do you want to insert it ?"
            if(insert){
               result =  myRepo.camRegister.insertMultiCamRegDay(camRegisterDay)
            }
        }

        _employee.value = myRepo.importer.getAllEmployeeInfo(folderPath,pList)

    }

    fun insertEmpResult(empList: List<CamRegisterDay>){


    }

    fun insert(){
        insert = true
    }
    fun onBackPress() {
        _isBackPressed.value = true
    }

}