package com.hrappv.data.local.datastoreimp

import com.hrappv.Absense
import com.hrappv.GetEmpResult
import com.hrappv.HrAppDb
import com.hrappv.data.local.datastore.EmpResultDataSource
import com.hrappv.data.models.AbsentDay
import com.hrappv.data.models.DayDetails
import com.hrappv.data.models.EmployeeResult
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class EmpResultDataSourceImpl @Inject constructor(
    hrAppDb: HrAppDb,
    val dispatcher: CoroutineDispatcher
) : EmpResultDataSource {

    val queries = hrAppDb.emp_resultQueries
    override fun getAllEmpResults(): Flow<List<EmployeeResult>> {
        return queries.getEmpResult(
            ::allEmpResultsMapper
        ).asFlow().mapToList()
    }


    private fun allEmpResultsMapper(
        empName: String,
        department_name: String,
        month: String?,
        year: String?,
        numberOfAttendDays: Int?,
        daysToCheckNoted: String?,
        numberOfAbsentDays: Int?,
        totalPartTime: Double?,
        partTimeDays: String?,
        totalEarlyTime: Double?,
        totalEarlyAccessTimeDays: String?,
//        absentDays: String?
    ) =
        EmployeeResult(
            name = empName,
            department = department_name,
            month = month ?: "",
            year = year ?: "",
            numberOfAttendDays = numberOfAttendDays ?: 0,
            daysToCheckNoted = daysToCheckNoted ?: "",
            numberOfAbsentDays = numberOfAbsentDays ?: 0,
            totalPartTime = totalPartTime ?: 0.0,
            partTimeDays = partTimeDays ?: "",
            totalEarlyTime = totalEarlyTime ?: 0.0,
            totalEarlyAccessTimeDays = totalEarlyAccessTimeDays ?: "",
//            absentDays = absentDays ?: "",

            )

    override fun getAllEmployeeResults(): List<EmployeeResult> {
        return queries.getEmpResult(::allEmpResultsMapper).executeAsList()
    }

    override fun getEmpResultByMonthAndYear(month: String, year: String): List<EmployeeResult> {
        return queries.getEmpResultByMonthAndYear(month, year, ::allEmpResultsMapper).executeAsList()
    }

    override suspend fun insertEmpResult(empResult: EmployeeResult) {
        withContext(dispatcher) {
            queries.transaction {
                val result =
                    queries.checkEmpResult(
                        fname = empResult.name,
                        month = empResult.month,
                        year = empResult.year
                    )
                        .executeAsOneOrNull()
                if (result == null) {
                    insertEmployeeResult(empResult)
                }

            }

        }
    }

    override suspend fun insertMultiEmpResult(empResultList: List<EmployeeResult>): String {
        var multiResult = ""

        withContext(dispatcher) {
            queries.transaction {
                empResultList.forEach { empResult ->
                    val result =
                        queries.checkEmpResult(
                            fname = empResult.name,
                            month = empResult.month,
                            year = empResult.year
                        ).executeAsOneOrNull()
                    if (result == null) {
                        insertEmployeeResult(empResult)
                        insertMultiEmployeeDayDetails(empResult.attendDays)
                        insertMultiEmployeeAbsentDays(empResult.absentDays)

                    }

                }
                afterCommit { multiResult = "All Employee Result are Register" }
                afterRollback { multiResult = "Register Day Fail" }
            }
        }
        return multiResult
    }

    override fun checkEmpResult(emp: String, month: String): GetEmpResult? {
        TODO("Not yet implemented")
    }

    private fun insertEmployeeResult(empResult: EmployeeResult) {
        queries.insertEmployeeResult(
            fname = empResult.name,
            department = empResult.department,
            month = empResult.month,
            year = empResult.year,
            numberOfAttendDays = empResult.numberOfAttendDays,
            daysToCheckNoted = empResult.daysToCheckNoted,
            numberOfAbsentDays = empResult.numberOfAbsentDays,
            totalPartTime = empResult.totalPartTime,
            partTimeDays = empResult.partTimeDays,
            totalEarlyTime = empResult.totalEarlyTime,
            totalEarlyAccessTimeDays = empResult.totalEarlyAccessTimeDays,
//            absentDays = empResult.absentDays

        )
    }

    /**
     * Day Details
     */
    override fun getAllDayDetails(): Flow<List<DayDetails>> {
        return queries.getAllDayDetails(mapper = { empName, department_name, month, year, day, wardia, typeOfDay, partTime, earlyAccess, earlyAccessNote, notes ->
            DayDetails(
                name = empName,
                department = department_name,
                day = day ?: "",
                month = month ?: "",
                year = year ?: "",
                wardia = wardia ?: "",
                typeOfDay = typeOfDay ?: "",
                partTime = partTime ?: 0.0,
                earlyAccess = earlyAccess ?: "",
                earlyAccessNote = earlyAccessNote ?: "",
                notes = notes ?: ""
            )
        }).asFlow().mapToList()
    }

    override fun getAllEmpDayDetails(): List<DayDetails> {
        return queries.getAllDayDetails(
            mapper = ::mapDayDetails

        ).executeAsList()
    }

    override fun getAllDayDetailsById(EmpName: String, month: String, year: String): List<DayDetails> {
        return queries.getAllDayDetailsById(
            EmpName,
            month, year,
            mapper = ::mapDayDetails
        ).executeAsList()
    }

    private fun mapDayDetails(
        empName: String,
        department_name: String,
        day: String?,
        month: String?,
        year: String?,
        wardia: String?,
        typeOfDay: String?,
        partTime: Double?,
        earlyAccess: String?,
        earlyAccessNote: String?,
        notes: String?
    ) = DayDetails(
        name = empName,
        department = department_name,
        month = month ?: "",
        year = year ?: "",
        day = day ?: "",
        wardia = wardia ?: "",
        typeOfDay = typeOfDay ?: "",
        partTime = partTime ?: 0.0,
        earlyAccess = earlyAccess ?: "",
        earlyAccessNote = earlyAccessNote ?: "",
        notes = notes ?: ""
    )

    override suspend fun insertEmpDayDetails(dayDetail: DayDetails) {
        withContext(dispatcher) {
            queries.transaction {
                val day =
                    queries.checkEmpDayDetail(
                        fname = dayDetail.name,
                        day = dayDetail.day,
                        month = dayDetail.month,
                        year = dayDetail.year
                    )
                        .executeAsOneOrNull()
                if (day == null) {
                    insertDayDetails(dayDetail)
                }
            }
        }
    }

    override suspend fun insertMultiEmpDayDetails(dayDetailList: List<DayDetails>) {
        withContext(dispatcher)
        {
            queries.transaction {
                dayDetailList.forEach { dayDetail ->
                    val day =
                        queries.checkEmpDayDetail(
                            fname = dayDetail.name,
                            day = dayDetail.day,
                            month = dayDetail.month,
                            year = dayDetail.year
                        )
                            .executeAsOneOrNull()
                    if (day == null) {
                        insertDayDetails(dayDetail)
                    }

                }
            }
        }
    }

    fun insertMultiEmployeeDayDetails(dayDetailList: List<DayDetails>) {
        queries.transaction {
            dayDetailList.forEach { dayDetail ->
                val day =
                    queries.checkEmpDayDetail(
                        fname = dayDetail.name,
                        day = dayDetail.day,
                        month = dayDetail.month,
                        year = dayDetail.year
                    )
                        .executeAsOneOrNull()
                if (day == null) {
                    insertDayDetails(dayDetail)
                }

            }
        }
    }

    fun insertMultiEmployeeAbsentDays(absentDayList: List<AbsentDay>) {
        queries.transaction {
            absentDayList.forEach { day ->
                val absentDay =
                    queries.checkEmpAbsentDay(
                        fname = day.name ?: "",
                        absnt_date = day.absnt_date.toString(),

                        )
                        .executeAsOneOrNull()
                if (absentDay == null) {
                    insertAbsentDay(day)
                }

            }
        }
    }

    fun insertAbsentDay(absentDay: AbsentDay) {
        queries.insertEmpAbsentDay(
            absnt_type = absentDay.absnt_type.toString(),
            absnt_date = absentDay.absnt_date.toString(),
            absnt_reason = absentDay.absnt_reason,
            fname = absentDay.name ?: "",
            department = absentDay.department ?: ""
        )
    }

    private fun insertDayDetails(dayDetail: DayDetails) {
        queries.insertEmpDayDetails(
            fname = dayDetail.name,
            department = dayDetail.department,
            day = dayDetail.day ?: "",
            month = dayDetail.month ?: "",
            year = dayDetail.year ?: "",
            wardia = dayDetail.wardia ?: "",
            typeOfDay = dayDetail.typeOfDay ?: "",
            partTime = dayDetail.partTime ?: 0.0,
            earlyAccess = dayDetail.earlyAccess ?: "",
            earlyAccessNote = dayDetail.earlyAccessNote ?: "",
            notes = dayDetail.notes ?: ""
        )

    }

    override suspend fun checkEmpDayDetail(emp: String, day: String, month: String, year: String): DayDetails? {
        TODO("Not yet implemented")
    }

    private fun checkEmp(): Long = queries.checkIfnoEmployee().executeAsOne()
}