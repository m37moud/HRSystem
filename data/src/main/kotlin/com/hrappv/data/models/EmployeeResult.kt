package com.hrappv.data.models

data class EmployeeResult(
//    val id: String = "",
    var name: String = "",
    val department: String = "",
    val numberOfAttendDays: Int = 0,
    val daysToCheckNoted: String = "",
    val numberOfAbsentDays: Int = 0,
    val totalPartTime: Double = 0.0,
    val partTimeDays: String = "",
    val totalEarlyTime: Double = 0.0,
    val totalEarlyAccessTimeDays: String = "",
    val absentDays: String = "no absent",
    val attendDays: List<DayDetails> = emptyList()

    ) {
    fun getMembers() = listOf(
//        id,
        name,
        department,
        numberOfAttendDays,
        daysToCheckNoted,
        numberOfAbsentDays,
        totalPartTime,
        partTimeDays,
        totalEarlyTime,
        totalEarlyAccessTimeDays,
        absentDays,
        attendDays
        )


}
