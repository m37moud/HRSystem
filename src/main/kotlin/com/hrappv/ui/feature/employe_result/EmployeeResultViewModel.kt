package com.hrappv.ui.feature.employe_result

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.hrappv.data.models.CamRegisterDay
import com.hrappv.data.models.EmployeeResult
import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import excel.ImportExcelFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import utils.LCE
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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


    private val _insertEmpResults = MutableStateFlow("")
    val insertEmpResults: StateFlow<String> = _insertEmpResults

    private val _startImporter = MutableStateFlow(false)
    val startImporter: StateFlow<Boolean> = _startImporter

    private val _screenMessage = MutableStateFlow<ScreenMessage?>(null)
    val screenMessage: StateFlow<ScreenMessage?> = _screenMessage


    private val _employee: MutableStateFlow<List<CamRegisterDay?>> = MutableStateFlow(emptyList())
    val employee: StateFlow<List<CamRegisterDay?>> = _employee

    private val _empResults: MutableStateFlow<LCE<List<EmployeeResult>>> = MutableStateFlow(LCE.NOACTION)
    val empResults: StateFlow<LCE<List<EmployeeResult>>> = _empResults

    var date by mutableStateOf<LocalDate?>(null)
        private set
    var insert by mutableStateOf(false)
        private set
    var message by mutableStateOf("")
        private set
    var result by mutableStateOf(emptyList<CamRegisterDay>())
        private set


    private val _isBackPressed = MutableStateFlow(false)
    val backToMain: StateFlow<Boolean> = _isBackPressed

    fun onClickMeClicked() {
        _folderPath.value = myRepo.getClickedWelcomeText()
    }

    suspend fun getEmployeeResults(folderPath: String) {
        _empResults.value = LCE.LOADING
        val camRegisterDayList = myRepo.camRegister.getAllCameraRegister()
//        val listEmpReporter = myRepo.importer.getEmployReport(camRegisterDayList)
//        _empResults.value = LCE.CONTENT(listEmpReporter)
    }


    /* TODO insert all exel in database */
    suspend fun registerDayByCam(folderPath: String? = null, pList: List<String>? = null) {

        if (date != null) {
            val month = date!!.monthValue.toString()
            val year = date!!.year.toString()
            val importer = ImportExcelFile(month, year)
            setStartImporter(true)
            val path = importer.getPath(folderPath, pList)
            if (path.isNotEmpty()) {
                val camRegisterDay = importer.getAllEmployeeInfo(path)
                if (camRegisterDay.isNotEmpty()) {

                    result = myRepo.camRegister.insertMultiCamRegDay(camRegisterDay)


                    if (result.isNotEmpty()) {
//                    dialogMessage(result)
                        _screenMessage.emit(ScreenMessage.ScafoldStateMessage("${result.size}"))

                        val listEmpReporter = importer.getEmployReport(result)
                        val insertMultiEmpResultResult = myRepo.empResult.insertMultiEmpResult(listEmpReporter)
                        _screenMessage.emit(ScreenMessage.ScafoldStateMessage(insertMultiEmpResultResult))

//                        dialogMessage(insertMultiEmpResultResult)


                    }else{
                        _screenMessage.emit(ScreenMessage.ScafoldStateMessage("employee is already in"))

                    }
                }

            } else {
                println("Folder is Empty")
//                dialogMessage("Folder is Empty")
                _screenMessage.emit(ScreenMessage.DialogStateMessage("Folder is Empty"))

                setStartImporter(false)

            }

        } else {
            _screenMessage.emit(ScreenMessage.DialogStateMessage("No Date Initialized"))
        }
//        launchOnIO {


//        }
//        if (camRegisterDay.isNotEmpty()) {
//            message = "import successful do you want to insert it ?"
//            if (insert) {
//            }
//        }

//        _employee.value = myRepo.importer.getAllEmployeeInfo(folderPath,pList)

    }

    fun insertEmpResult(empList: List<CamRegisterDay>) {


    }

    fun dialogMessage(message: String = "") {
        _insertEmpResults.value = message
    }

    fun insert() {
        insert = true
    }

    fun setDate(dateTime: String) {
        val pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        date = LocalDate.parse(dateTime, pattern)
    }

    fun setStartImporter(state: Boolean) {
        _startImporter.value = state
    }

    fun onBackPress() {
        _isBackPressed.value = true
    }

    sealed class ScreenMessage {
        data class ScafoldStateMessage(val message: String) : ScreenMessage()
        data class DialogStateMessage(val message: String) : ScreenMessage()
    }


}