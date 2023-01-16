package com.hrappv.data.local.datastore

import com.hrappv.*
import com.hrappv.data.models.Employee
import com.hrappv.data.models.Employees

interface ViewEmpDataSource {


     fun getAllEmployees(): List<GetAllEmployees>

    suspend fun getEmployeeByName(name : String): List<GetEmployeeByName>

    suspend fun deleteEmployee(id: Long)

    suspend fun getEmployeeByID(id:Long) : GetEmployeeByID?

    suspend fun insertEmployee(employee : Employees)



}