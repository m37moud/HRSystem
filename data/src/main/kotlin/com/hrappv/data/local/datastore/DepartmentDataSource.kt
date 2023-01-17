package com.hrappv.data.local.datastore

import com.hrappv.data.models.Department
import com.hrappv.data.models.Employees

interface DepartmentDataSource {

    suspend fun checkDepartment(id: Long)

    suspend fun insertDepartment(
        department: Department
    )


    suspend fun insertMultiDepartment(departments: List<Department>): Boolean
}