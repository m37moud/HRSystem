package com.hrappv.data.local.datastore

import com.hrappv.GetEmpResult
import com.hrappv.data.models.DayDetails
import com.hrappv.data.models.EmployeeResult
import kotlinx.coroutines.flow.Flow


interface EmpResultDataSource {

    fun getAllEmpResults(): Flow<List<GetEmpResult>>
    fun getAllEmployeeResults(): List<GetEmpResult>

    suspend fun insertEmpResult(empResult: EmployeeResult)

    suspend fun insertMultiEmpResult(empResultList: List<EmployeeResult>) : String

    fun checkEmpResult(emp: String, month: String): GetEmpResult?

    /**
     * Day Details
     */
    fun getAllDayDetails(): Flow<List<DayDetails>>
    fun getAllEmpDayDetails(): List<DayDetails>
    suspend fun insertEmpDayDetails(dayDetail: DayDetails)

    suspend fun insertMultiEmpDayDetails(dayDetail: List<DayDetails>)
    suspend  fun checkEmpDayDetail(emp: String,day: String, month: String, year: String): DayDetails?


}