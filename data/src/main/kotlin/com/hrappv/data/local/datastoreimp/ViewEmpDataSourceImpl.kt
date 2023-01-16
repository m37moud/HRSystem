package com.hrappv.data.local.datastoreimp

import com.hrappv.*
import com.hrappv.data.local.datastore.ViewEmpDataSource
import com.hrappv.data.models.Employee
import com.hrappv.data.models.Employees
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ViewEmpDataSourceImpl @Inject constructor(
    hrAppDb: HrAppDb,
    private val dispatcher: CoroutineDispatcher
) : ViewEmpDataSource {

    val queries = hrAppDb.employeQueries


    override fun getAllEmployees(): Flow<List<GetAllEmployees>> {
        return queries.getAllEmployees().asFlow().mapToList()
    }

    override suspend fun getEmployeeByName(name: String): List<GetEmployeeByName> {
        return withContext(dispatcher) {
            queries.getEmployeeByName(name).executeAsList()
        }
    }

    override suspend fun deleteEmployee(id: Long) {
         withContext(dispatcher) {
            try {
//              val r = queries.transaction  {
//                 afterRollback { println("No employee were delete.") }
//                   afterCommit { println("$ players were delete.") }
                   queries.deleteEmployee(id)
//               }


            } catch (e: Exception) {
                println("${e.message}")
            }
        }
    }

    override suspend fun getEmployeeByID(id: Long): GetEmployeeByID? {
        return withContext(dispatcher) {
            queries. getEmployeeByID(id).executeAsOneOrNull()

        }
    }

    override suspend fun insertEmployee(employee: Employees) {
        withContext(dispatcher) {
            queries.insertEmployee(
                emp_id = employee.emp_id,
                id = employee.id,
                fname = employee.fname,
                totaldays = employee.totaldays,
                bith_day = employee.bith_day,
                salary = employee.salary,
                vacanition = employee.vacanition,
                vbalance = employee.vbalance,
                bdl_balance = employee.bdl_balance,
                department = employee.department_name,
            )
        }

    }

    suspend fun InsertMultiEmployee(employees: List<Employees>) {
        withContext(dispatcher) {
            queries.transaction {
                employees.forEach { emp ->
                    val employee = queries.getEmployeeByID(emp.emp_id)?.executeAsOneOrNull()
                    if (employee != null) {
                        insertEmp(emp)
                    }

                }

            }
        }
    }

    private fun insertEmp(employee: Employees) {
        queries.insertEmployee(
            emp_id = employee.emp_id,
            id = employee.id,
            fname = employee.fname,
            totaldays = employee.totaldays,
            bith_day = employee.bith_day,
            salary = employee.salary,
            vacanition = employee.vacanition,
            vbalance = employee.vbalance,
            bdl_balance = employee.bdl_balance,
            department = employee.department_name,
        )

    }
}