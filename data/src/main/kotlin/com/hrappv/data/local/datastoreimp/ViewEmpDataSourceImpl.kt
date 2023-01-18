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


    override fun getAllEmployees(): List<GetAllEmployees> {
        return queries.getAllEmployees().executeAsList()
    }

    override fun getEmployeeByName(name: String): List<GetEmployeeByName> {
        return queries.getEmployeeByName(name).executeAsList()
//        return withContext(dispatcher) {
//            queries.getEmployeeByName(name).executeAsList()
//        }
    }

    override suspend fun deleteEmployee(id: Long) :Boolean{
        var delete = false
        withContext(dispatcher) {
            try {
                queries.transactionWithResult {
                    afterRollback {
                        delete = false
                        println("No employee were delete.")
                    }
                    afterCommit {
                        delete = true
                        println("$ players were delete.")
                    }
                    queries.deleteEmployee(id)
                }


            } catch (e: Exception) {
                println("${e.message}")
            }
        }
        return delete
    }

    override suspend fun getEmployeeByID(id: Long): GetEmployeeByID? {
        return withContext(dispatcher) {
            queries.getEmployeeByID(id).executeAsOneOrNull()

        }
    }

    override suspend fun insertEmployee(employee: Employees) {
        withContext(dispatcher) {
            queries.insertEmployee(
                emp_id = employee.emp_id,
//                id = employee.id,
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

    override suspend fun insertMultiEmployee(employees: List<Employees>) : Boolean {
        var insert = false

        withContext(dispatcher) {
            queries.transaction {
                employees.forEach { emp ->
                    val employee = queries.getEmployeeByID(emp.emp_id).executeAsOneOrNull()
                    if (employee == null) {
                        insertEmp(emp)
                    }

                }
                afterRollback {
                    insert = false
                    println("No employee inserted.")
                }
                afterCommit {
                    insert = true
                    println("employees. were inserted.")
                }
            }
        }
        return insert
    }

    private fun insertEmp(employee: Employees) {
        queries.insertEmployee(
            emp_id = employee.emp_id,
//            id = employee.id,
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