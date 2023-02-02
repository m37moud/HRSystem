package com.hrappv.data.local.datastoreimp

import com.hrappv.HrAppDb
import com.hrappv.data.local.datastore.EmpResultDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class EmpResultDataSourceImpl @Inject constructor(
    val hrAppDb: HrAppDb,
    dispatcher: CoroutineDispatcher
) : EmpResultDataSource {
}