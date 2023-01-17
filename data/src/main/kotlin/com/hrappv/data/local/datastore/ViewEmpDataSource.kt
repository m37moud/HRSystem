package com.hrappv.data.local.datastore

import com.hrappv.*
import com.hrappv.data.models.Employee
import com.hrappv.data.models.Employees
import kotlinx.coroutines.flow.Flow

interface ViewEmpDataSource {


     fun getAllEmployees(): List<GetAllEmployees>
     fun getEmployeeByName(name : String): List<GetEmployeeByName>

    suspend fun deleteEmployee(id: Long):Boolean

    suspend fun getEmployeeByID(id:Long) : GetEmployeeByID?

    suspend fun insertEmployee(employee : Employees)



}