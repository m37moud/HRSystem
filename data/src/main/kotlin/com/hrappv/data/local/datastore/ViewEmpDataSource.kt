package com.hrappv.data.local.datastore

import com.hrappv.*
import com.hrappv.data.models.Employee
import com.hrappv.data.models.Employees
import kotlinx.coroutines.flow.Flow

interface ViewEmpDataSource {


    fun getAllEmployees(): List<GetAllEmployees>
    fun getEmployeeByName(name: String): List<GetEmployeeByName>
    fun getEmployeeByDepartment(departmentID: Long): List<GetEmployeeByDepartment>
    fun getEmployeeLikeID(name: String): List<GetEmployeeLikeID>

    suspend fun deleteEmployee(id: Long): Boolean

    suspend fun getEmployeeByID(id: Long): GetEmployeeByID?

    suspend fun insertEmployee(employee: Employees): String

    suspend fun insertMultiEmployee(employees: List<Employees>): Boolean


}