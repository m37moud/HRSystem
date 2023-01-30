package com.hrappv.data.local.datastoreimp

import com.hrappv.Cam_register_d
import com.hrappv.HrAppDb
import com.hrappv.data.local.datastore.CamRegisterDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CamRegisterDataSourceImpl @Inject constructor(
    hrDb: HrAppDb,
    private val dispatcher: CoroutineDispatcher
) : CamRegisterDataSource{

    val queries = hrDb.cam_register_dQueries
    override fun insertCamRegister(): Flow<List<Cam_register_d>> {
        TODO("Not yet implemented")
    }

}