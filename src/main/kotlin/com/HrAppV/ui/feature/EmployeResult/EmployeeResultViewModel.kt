package com.HrAppV.ui.feature.EmployeResult

import com.HrAppV.data.di.module.EmployeeResult
import com.HrAppV.data.repo.MyRepo
import com.HrAppV.util.ViewModel
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


    private val _EmpResults: MutableStateFlow<LCE<List<EmployeeResult>>> = MutableStateFlow(LCE.NOACTION)
    val empResults: StateFlow<LCE<List<EmployeeResult>>> = _EmpResults

    fun onClickMeClicked() {
        _folderPath.value = myRepo.getClickedWelcomeText()
    }

    suspend fun getEmployeResults(folderPath: String) {
        _EmpResults.value = LCE.LOADING
        _EmpResults.value = myRepo.importer.getEmployReport(folderPath)
    }


}