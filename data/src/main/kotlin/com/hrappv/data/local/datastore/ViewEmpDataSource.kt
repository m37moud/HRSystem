package com.hrappv.data.local.datastore

import com.hrappv.Employe
import com.hrappv.GetAllEmployees
import com.hrappv.GetEmployeeByName
import com.hrappv.User
import com.hrappv.data.models.Employee
import com.hrappv.data.models.Employees

interface ViewEmpDataSource {


    suspend fun getEmployeeByName(name : String): List<GetEmployeeByName>
     fun getAllEmployees(): List<GetAllEmployees>

}