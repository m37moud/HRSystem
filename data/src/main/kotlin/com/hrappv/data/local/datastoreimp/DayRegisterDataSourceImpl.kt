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

    override  fun insertRegisterDay(dayRegister: RegisterAttends): Boolean {
        var insert = false

//        withContext(dispatcher) {
            queries.transaction {
                val day = queries.checkRegisterDay(
                    fname = dayRegister.emp_name!!,
                    department = dayRegister.department!!,
                    oDATE = dayRegister.oDate,

                    ).executeAsOneOrNull()

                if (day == null) {

                    insertDay(dayRegister)
                } else {
                    val temp =  dayRegister.copy(
//                      fname = day.emp_name!!,
//                      department = day.department!!,
//                      oDATE = day.oDate,
                        day = day.day ?: dayRegister.day ,
                        status = day.status ?: dayRegister.status,
                        in_time = day.in_time ?: dayRegister.in_time,
                        out_time = day.out_time ?: dayRegister.out_time,
                        late = day.late ?: dayRegister.late,
                        early = day.early ?: dayRegister.early,
                    )
                    println(temp.toString())


                    updateDay(temp)
                }
                afterCommit { insert = true }
                afterRollback { insert = false }
            }


//        }
        return insert
    }

     fun insertDay(day: RegisterAttends) {
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

     fun updateDay(day: RegisterAttends) {
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
                  val temp =  registerDay.copy(
//                      fname = day.emp_name!!,
//                      department = day.department!!,
//                      oDATE = day.oDate,
                      day = day.day ?: registerDay.day ,
                      status = day.status ?: registerDay.status,
                      in_time = day.in_time ?: registerDay.in_time,
                      out_time = day.out_time ?: registerDay.out_time,
                      late = day.late ?: registerDay.late,
                      early = day.early ?: registerDay.early,
                  )
                    println(temp.toString())

                    updateDay(temp)
                }


            }

            afterCommit { multiInsert = true }
            afterRollback { multiInsert = false }
        }
//        }


        return multiInsert
    }
}