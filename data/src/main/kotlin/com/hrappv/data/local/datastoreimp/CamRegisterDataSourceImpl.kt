package com.hrappv.data.local.datastoreimp

import com.hrappv.Cam_register_d
import com.hrappv.GetAllCamRegisterDay
import com.hrappv.HrAppDb
import com.hrappv.data.local.datastore.CamRegisterDataSource
import com.hrappv.data.models.CamRegisterDay
import com.hrappv.data.models.RegisterAttends
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.time.LocalTime
import javax.inject.Inject

class CamRegisterDataSourceImpl @Inject constructor(
    hrDb: HrAppDb,
    private val dispatcher: CoroutineDispatcher
) : CamRegisterDataSource {

    val queries = hrDb.cam_register_dQueries
    override fun getAllCamRegister(): Flow<List<GetAllCamRegisterDay>> {
        return queries.getAllCamRegisterDay().asFlow().mapToList()
    }

    override fun getAllCameraRegister(): List<CamRegisterDay> {
        return queries.getAllCamRegisterDay(mapper = {empName, department_name, oDATE, day, time, hour, status, shift ->
            CamRegisterDay(empName=empName,departName=department_name,oDATE=oDATE,day=day,time=time,hour=hour,status=status, shift = shift)
        }).executeAsList()
    }

    override  fun insertCamRegisterDay(camRegister: CamRegisterDay): String {
        var result = ""
//        withContext(dispatcher) {
            queries.transaction {
                val isFound =
                    queries.checkRegisterCam(camRegister.empName!!, camRegister.oDATE,camRegister.hour, camRegister.status).executeAsOneOrNull()
                if (isFound == null) {
                    insertDay(camRegister)
                }else{
                    result = "this day is already in"
                }


                afterCommit { result = " Day is Register" }
                afterRollback { result = "Register Day Fail" }
            }
//        }
        return result
    }

    override  fun insertMultiCamRegDay(camRegList: List<CamRegisterDay>): String {
        var multiResult = ""
//        withContext(dispatcher) {
            queries.transaction {
                camRegList.forEach { regDay ->
                    val isFound =
                        queries.checkRegisterCam(regDay.empName!!, regDay.oDATE,regDay.hour, regDay.status).executeAsOneOrNull()
                    if (isFound == null) {
                        insertDay(regDay)
                    }
                }


                afterCommit { multiResult = "All Days are Register" }
                afterRollback { multiResult = "Register Day Fail" }
            }

//        }

        return multiResult
    }

    private fun insertDay(camReg: CamRegisterDay) {
        queries.insertRegisterCam(
            fname = camReg.empName!!,
            department = camReg.departName!!,
            oDATE = camReg.oDATE,
            day = camReg.day,
            status = camReg.status,
            time = camReg.time,
            hour = camReg.hour,
            shift = camReg.shift

            )

    }

    override suspend fun checkCamRegister(emp: String, oDate: String,hour: String, statue: String): Cam_register_d? {
        return withContext(dispatcher) {
            queries.checkRegisterCam(emp, oDate,hour, statue).executeAsOneOrNull()
        }
    }


}