package com.hrappv.data.local.datastoreimp

import com.hrappv.GetEmpResult
import com.hrappv.HrAppDb
import com.hrappv.data.local.datastore.EmpResultDataSource
import com.hrappv.data.models.EmployeeResult
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmpResultDataSourceImpl @Inject constructor(
    hrAppDb: HrAppDb,
    val dispatcher: CoroutineDispatcher
) : EmpResultDataSource {

    val queries = hrAppDb.emp_resultQueries
    override fun getAllEmpResults(): Flow<List<GetEmpResult>> {
        return queries.getEmpResult().asFlow().mapToList()
    }

    override suspend fun insertEmpResult(empResult: GetEmpResult) {
        withContext(dispatcher) {
            queries.transaction {
                val result = queries.checkEmpResult(fname = empResult.empName, month = empResult.month).executeAsOneOrNull()
                if(result == null){
                    insertEmployeeResult(empResult)
                }

            }

        }
    }

    override suspend fun insertMultiEmpResult(empResultList: List<GetEmpResult>) {
        withContext(dispatcher){
            queries.transaction {
                empResultList.forEach {empResult ->
                    val result = queries.checkEmpResult(fname = empResult.empName, month = empResult.month).executeAsOneOrNull()
                    if(result == null){
                        insertEmployeeResult(empResult)
                    }

                }
            }
        }
    }

    override fun checkEmpResult(emp: String, month: String): GetEmpResult? {
        TODO("Not yet implemented")
    }

     private fun insertEmployeeResult(empResult: GetEmpResult) {
            queries.insertEmployeeResult(
                fname = empResult.empName,
                department = empResult.department_name,
                month = empResult.month,
                year =empResult.year,
                numberOfAttendDays = empResult.numberOfAttendDays,
                daysToCheckNoted = empResult.daysToCheckNoted,
                numberOfAbsentDays = empResult.numberOfAbsentDays,
                totalPartTime = empResult.totalPartTime,
                partTimeDays = empResult.partTimeDays,
                totalEarlyTime = empResult.totalEarlyTime,
                totalEarlyAccessTimeDays = empResult.totalEarlyAccessTimeDays,
                absentDays = empResult.absentDays

            )
        }

}