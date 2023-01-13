package com.hrappv.data.local.datastoreimp

import com.hrappv.*
import com.hrappv.data.local.datastore.ViewEmpDataSource
import com.hrappv.data.models.Employee
import com.hrappv.data.models.Employees
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ViewEmpDataSourceImpl @Inject constructor(hrAppDb: HrAppDb ,
private val dispatcher : CoroutineDispatcher
) : ViewEmpDataSource {

    val queries = hrAppDb.employeQueries

    override suspend fun getEmployeeByName(name: String): List<GetEmployeeByName> {
        return withContext(dispatcher){
            queries.getEmployeeByName(name).executeAsList()
        }
    }

    override fun getAllEmployees(): List<GetAllEmployees> {
       return queries.getAllEmployees().executeAsList()
    }

}