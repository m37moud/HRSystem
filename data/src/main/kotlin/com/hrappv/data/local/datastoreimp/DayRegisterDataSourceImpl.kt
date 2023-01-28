package com.hrappv.data.local.datastoreimp

import com.hrappv.Day_register
import com.hrappv.GetAllDaysRegister
import com.hrappv.HrAppDb
import com.hrappv.data.local.datastore.DayRegisterDataSource
import com.hrappv.data.local.datastore.DepartmentDataSource
import com.hrappv.data.models.Employees
import com.hrappv.data.models.RegisterAttends
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DayRegisterDataSourceImpl @Inject constructor(
    hrDb: HrAppDb,
    private val dispatcher: CoroutineDispatcher
) : DayRegisterDataSource {

    private val queries = hrDb.day_registerQueries
    override fun getAllDaysRegister(): Flow<List<GetAllDaysRegister>> {
        return queries.getAllDaysRegister().asFlow().mapToList()
    }

    override suspend fun checkRegisterDay(emp: String, depart: String, oDate: String): Day_register? {
        return withContext(dispatcher) {
            queries.checkRegisterDay(
                fname = emp,
                department = depart,
                oDATE = oDate
            ).executeAsOneOrNull()

        }
    }

    override suspend fun insertRegisterDay(day: RegisterAttends): Boolean {
        var insert = false

        withContext(dispatcher) {
            queries.transaction {
                val dayRegister = queries.checkRegisterDay(
                    fname = day.emp_name!!,
                    department = day.department!!,
                    oDATE = day.oDate,

                    ).executeAsOneOrNull()

                if (dayRegister == null) {

                    insertDay(day)
                } else {

                    updateDay(day)
                }
                afterCommit { insert = true }
                afterRollback { insert = false }
            }


        }
        return insert
    }

    private fun insertDay(day: RegisterAttends) {
        queries.insertRegisterDay(
            fname = day.emp_name!!,
            department = day.department!!,
            oDATE = day.oDate,
            day = day.day,
            status = day.status,
            in_time = day.in_time,
            out_time = day.out_time,
            late = day.late,
            early = day.early,
        )

    }

    private fun updateDay(day: RegisterAttends) {
        queries.updateTheDay(
            fname = day.emp_name!!,
            department = day.department!!,
            oDATE = day.oDate,
            day = day.day,
            status = day.status,
            in_time = day.in_time,
            out_time = day.out_time,
            late = day.late,
            early = day.early,
        )

    }

    override suspend fun updateTheDay(day: RegisterAttends): Boolean {
        var updated = false
        withContext(dispatcher) {
            queries.transaction {
                queries.updateTheDay(
                    fname = day.emp_name!!,
                    department = day.department!!,
                    oDATE = day.oDate,
                    day = day.day,
                    status = day.status,
                    in_time = day.in_time,
                    out_time = day.out_time,
                    late = day.late,
                    early = day.early,
                )
                afterCommit { updated = true }
                afterRollback { updated = false }
            }
        }
        return updated
    }

    override fun insertMultiDaysRegister(daysRegister: List<RegisterAttends>): Boolean {
        var multiInsert = false
//        withContext(dispatcher){
        queries.transaction {
            daysRegister.forEach { registerDay ->
                val day = queries.checkRegisterDay(
                    fname = registerDay.emp_name!!,
                    department = registerDay.department!!,
                    oDATE = registerDay.oDate

                ).executeAsOneOrNull()
                println(day.toString())
                if (day == null) {

                    insertDay(registerDay)
                } else { // to update non null variable
                    registerDay.copy(in_time = day.in_time)
                    updateDay(registerDay)
                }


            }

            afterCommit { multiInsert = true }
            afterRollback { multiInsert = false }
        }
//        }


        return multiInsert
    }
}