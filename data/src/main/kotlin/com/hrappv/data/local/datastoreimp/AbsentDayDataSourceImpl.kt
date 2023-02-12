package com.hrappv.data.local.datastoreimp

import com.hrappv.HrAppDb
import com.hrappv.data.local.datastore.AbsentDayDataSource
import com.hrappv.data.local.datastore.DayDetailsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AbsentDayDataSourceImpl @Inject constructor(
    val hrAppDb: HrAppDb,
    dispatcher: CoroutineDispatcher
) : AbsentDayDataSource {
}