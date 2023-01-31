package com.hrappv.data.local.datastore

import com.hrappv.Cam_register_d
import com.hrappv.GetAllCamRegisterDay
import com.hrappv.data.models.CamRegisterDay
import kotlinx.coroutines.flow.Flow

interface CamRegisterDataSource {


    fun getAllCamRegister(): Flow<List<GetAllCamRegisterDay>>

    fun insertCamRegisterDay(camRegister: CamRegisterDay): String

    fun insertMultiCamRegDay(camRegList: List<CamRegisterDay>): String


    suspend fun checkCamRegister(emp: String, oDate: String, hour: String, statue: String): Cam_register_d?
}