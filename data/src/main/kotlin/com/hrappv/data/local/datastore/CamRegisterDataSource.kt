package com.hrappv.data.local.datastore

import com.hrappv.Cam_register_d
import kotlinx.coroutines.flow.Flow

interface CamRegisterDataSource {


    fun insertCamRegister() : Flow<List<Cam_register_d>>

    fun checkCamRegister()
}