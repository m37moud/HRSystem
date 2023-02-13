package com.hrappv.ui.feature.employe_result.emp_result_detail.absent_days

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.hrappv.data.models.AbsentDay
import com.hrappv.data.repo.MyRepo
import com.hrappv.util.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import utils.LCE
import java.time.LocalDate
import javax.inject.Inject

class AbsentDaysDetailViewModel @Inject constructor(
    private val myRepo: MyRepo,
    // Inject your repos here...
) : ViewModel() {
    private val _absentDays: MutableStateFlow<LCE<List<AbsentDay>>?> = MutableStateFlow(null)
    val absentDays: StateFlow<LCE<List<AbsentDay>>?> = _absentDays

    var date by mutableStateOf<LocalDate?>(null)
        private set

    fun setAbsentList(empname: String , month :String , year : String) {
            _absentDays.value = LCE.LOADING
            val absentList = myRepo.empResult.getAbsentListBy(empname,month, year)
            _absentDays.value = LCE.CONTENT(absentList)

    }
}