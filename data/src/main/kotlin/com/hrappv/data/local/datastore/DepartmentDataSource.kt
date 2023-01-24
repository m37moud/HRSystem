package com.hrappv.data.local.datastore

import com.hrappv.data.models.Department
import kotlinx.coroutines.flow.Flow
import utils.LCE

interface DepartmentDataSource {

    suspend fun checkDepartment(id: Long)

    suspend fun insertDepartment(
        department: Department
    ): String


    suspend fun insertMultiDepartment(departments: List<Department>): Boolean

    fun getAllDepartments() : Flow<List<Department>>

//    fun getDepartment() : List<>
}