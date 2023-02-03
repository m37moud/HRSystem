package com.hrappv.data.local.datastore

import com.hrappv.GetEmpResult
import com.hrappv.data.models.EmployeeResult
import kotlinx.coroutines.flow.Flow


interface EmpResultDataSource {

    fun getAllEmpResults() : Flow<List<GetEmpResult>>

    suspend fun insertEmpResult(empResult: GetEmpResult)

    suspend fun insertMultiEmpResult(empResultList: List<GetEmpResult>)

    fun checkEmpResult(emp : String , month : String) : GetEmpResult?



}