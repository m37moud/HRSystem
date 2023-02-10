package com.hrappv.ui.feature.employe_result.register_attends

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.hrappv.data.models.CamRegisterDay
import com.hrappv.data.models.EmployeeResult
import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import com.toxicbakery.logging.Arbor
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

//
//    private val _employeeResult: MutableStateFlow<List<CamRegisterDay?>> = MutableStateFlow(emptyList())
//    val employeeResult: StateFlow<List<CamRegisterDay?>> = _employeeResult

    val employee = myRepo.empResult.getAllEmpResults()

    private val _empResults: MutableStateFlow<LCE<List<EmployeeResult>>?> = MutableStateFlow(null)
    val empResults: StateFlow<LCE<List<EmployeeResult>>?> = _empResults


    var date by mutableStateOf<LocalDate?>(null)
        private set
    var insert by mutableStateOf(false)
        private set
    var message by mutableStateOf("")
        private set
    var result by mutableStateOf(emptyList<CamRegisterDay>())
        private set

    private val _resultDetailsPressed = MutableStateFlow<EmployeeResult?>(null)
    val resultDetailsPressed: StateFlow<EmployeeResult?> = _resultDetailsPressed

    private val _isResultDetailsPressed = MutableStateFlow(false)
    val isResultDetailsPressed: StateFlow<Boolean> = _isResultDetailsPressed

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

    fun setEmpResult(empResult: List<EmployeeResult>) {
        _empResults.value = LCE.LOADING
        _empResults.value = LCE.CONTENT(empResult)
    }

    suspend fun getResultByDate() {
        _empResults.value = LCE.LOADING
        val month = date!!.monthValue.toString()
        val year = date!!.year.toString()
        val result = myRepo.empResult.getEmpResultByMonthAndYear(month, year)
        if (result.isNotEmpty())
            _empResults.value = LCE.CONTENT(result)
        else
            setEmpResultError("No Employees Result For this month")

    }

    fun setEmpResultError(error: String) {
        _empResults.value = LCE.ERROR(error)
    }

    /* TODO insert all exel in database */
    suspend fun registerDayByCam(folderPath: String? = null, pList: List<String>? = null) {
        _screenMessage.emit(null)

        if (date != null) {
            val month = date!!.monthValue.toString()
            val year = date!!.year.toString()
            val importer = ImportExcelFile(month, year)
            setStartImporter(true)
            val path = importer.getPath(folderPath, pList)
            if (path.isNotEmpty()) {
                val camRegisterDay = importer.getAllEmployeeInfo(path)
                if (camRegisterDay.isNotEmpty()) {
                    _screenMessage.emit(ScreenMessage.ScafoldStateMessage("start insert all days  = ${camRegisterDay.size} please wait..."))

                    result = myRepo.camRegister.insertMultiCamRegDay(camRegisterDay)

                    Arbor.d(result.joinToString("\n"))
                    if (result.isNotEmpty()) {
//                    dialogMessage(result)
                        _screenMessage.emit(ScreenMessage.ScafoldStateMessage("calculate employee attends result days ${result.size} please wait..."))

                        val listEmpReporter = importer.getEmployReport(result)
                        _screenMessage.emit(ScreenMessage.ScafoldStateMessage("start insert employee attends result days ${result.size} please wait..."))

                        val insertMultiEmpResultResult = myRepo.empResult.insertMultiEmpResult(listEmpReporter)
                        _screenMessage.emit(ScreenMessage.ScafoldStateMessage(insertMultiEmpResultResult))

//                        dialogMessage(insertMultiEmpResultResult)


                    } else {
                        _screenMessage.emit(ScreenMessage.ScafoldStateMessage("employee is already in"))

                    }
                } else {
                    Arbor.d("please select correct month")
                    _screenMessage.emit(ScreenMessage.DialogStateMessage(" please select correct month", true))


                }

            } else {
                println("Folder is Empty")
//                dialogMessage("Folder is Empty")
                _screenMessage.emit(ScreenMessage.DialogStateMessage("Folder is Empty", error = true))
            }

        } else {
            _screenMessage.emit(ScreenMessage.DialogStateMessage("No Date Initialized", error = true))
        }
        setStartImporter(false)
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

    fun onStartResultDetails(empResult: EmployeeResult) {
        _isResultDetailsPressed.value = true
        _resultDetailsPressed.value = empResult

    }

    sealed class ScreenMessage {
        data class ScafoldStateMessage(val message: String) : ScreenMessage()
        data class DialogStateMessage(val message: String, val error: Boolean = false) : ScreenMessage()
    }


}