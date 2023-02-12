package com.hrappv.data.models

import com.hrappv.data.util.AbsentType
import java.time.LocalDate

data class AbsentDay(
 val absnt_type: AbsentType = AbsentType.RegularVacation,
 val absnt_date: LocalDate?,
 val absnt_reason: String?,
 val name: String?,
 val department: String?
)
