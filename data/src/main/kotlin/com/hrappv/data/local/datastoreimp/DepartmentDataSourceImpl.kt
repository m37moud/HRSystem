package com.hrappv.data.local.datastoreimp

import com.hrappv.HrAppDb
import com.hrappv.data.local.datastore.DepartmentDataSource
import com.hrappv.data.local.datastore.UserDataSource
import com.hrappv.data.models.Department
import com.hrappv.data.models.Employees
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DepartmentDataSourceImpl @Inject constructor(
    hrDb: HrAppDb,
    private val dispatcher: CoroutineDispatcher
) : DepartmentDataSource {
    private val queries = hrDb.departmentQueries

    override suspend fun insertMultiDepartment(departments: List<Department>): Boolean {
        var insert = false
        withContext(dispatcher) {
            queries.transaction {
                departments.forEach { depart ->
                    println(depart.department)
                    val department =
                        depart.department.let { queries.selectDepartmentByName(it!!).executeAsOneOrNull() }
                    println(department.toString())
                    if (department == null) {
                        insertDepart(depart)
                    }

                }
                afterRollback {
                    insert = false
                    println("No department inserted.")
                }
                afterCommit {
                    insert = true
                    println("department. were inserted.")
                }
            }
        }

        return insert
    }

    override fun getAllDepartments(): Flow<List<Department>> {
        return queries.getAllDepartments(mapper = { depart_id, department, commetion_rate, depart_type, commetion_type, commetion_month ->
            Department(depart_id, department, commetion_rate, depart_type, commetion_type, commetion_month)
        }).asFlow().mapToList()
    }

    override fun checkIfNoDepart(): Long {
        return queries.checkIfnoDepart().executeAsOne()
    }

    override suspend fun checkDepartment(id: Long) {
        val department = queries.selectDepartmentById(id)

    }

    private fun insertDepart(
        department: Department
    ) {
        println(department.toString())
        try {
            queries.insertDepartMent(
//            depart_id = department.depart_id,
                department = department.department!!,
                commetion_rate = department.commetion_rate,
                depart_type = department.depart_type,
                commetion_type = department.commetion_type,
                commetion_month = department.commetion_month,
            )
        } catch (exc: Exception) {


            println(exc.message.toString())
            return
        }

    }


    override suspend fun insertDepartment(
        department: Department
    ): String {
        var result = ""
        try {
            withContext(dispatcher) {
                queries.transaction {

                    val depart = department.let { queries.selectDepartmentByName(it.department).executeAsOneOrNull() }
                    println(depart.toString())
                    result = if (depart == null) {
                        insertDepart(department)
                        println("department is inserted")

                        " department is inserted"
                    } else {
                        println("this department is already found ")

                        "this department is already found"
                    }

                    afterRollback {
                        println("some thing goes wrong")

                        result = "some thing goes wrong"
                    }
                }

            }
        } catch (exc: Exception) {

            result = exc.message.toString()

            println(result)
            return result
        }
        return result
    }


}