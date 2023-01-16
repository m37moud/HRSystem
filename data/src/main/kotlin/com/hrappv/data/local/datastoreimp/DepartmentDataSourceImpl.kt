package com.hrappv.data.local.datastoreimp

import com.hrappv.Department
import com.hrappv.HrAppDb
import com.hrappv.data.local.datastore.DepartmentDataSource
import com.hrappv.data.local.datastore.UserDataSource
import com.hrappv.data.models.Employees
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DepartmentDataSourceImpl @Inject constructor(
    hrDb: HrAppDb,
    private val dispatcher: CoroutineDispatcher
) : DepartmentDataSource {
    private val queries = hrDb.departmentQueries

    suspend fun InsertMultiDepartment(departments: List<Department>) {
        withContext(dispatcher) {
            queries.transaction {
                departments.forEach { depart ->
                    val department = queries.selectRocketById(depart.depart_id)?.executeAsOneOrNull()
                    if (department != null) {
                        insertDepart(depart)
                    }

                }

            }
        }
    }

    override suspend fun checkDepartment(id: Long) {
        val department = queries.selectRocketById(id)

    }
    private fun insertDepart(
        department: Department
    ) {
            queries.insertDepartMent(
                depart_id = department.depart_id,
                department = department.department,
                commetion_rate = department.commetion_rate,
                depart_type = department.depart_type,
                commetion_type = department.commetion_type,
                commetion_month = department.commetion_month,
            )
    }


    override suspend fun insertDepartment(
        department: Department
    ) {
        withContext(dispatcher) {
            queries.insertDepartMent(
                depart_id = department.depart_id,
                department = department.department,
                commetion_rate = department.commetion_rate,
                depart_type = department.depart_type,
                commetion_type = department.commetion_type,
                commetion_month = department.commetion_month,
            )
        }
    }


}