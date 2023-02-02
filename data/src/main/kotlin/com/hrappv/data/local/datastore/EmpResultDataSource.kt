package com.hrappv.data.local.datastore

import com.hrappv.GetEmpResult
import kotlinx.coroutines.flow.Flow


interface EmpResultDataSource {

    fun getAllEmpResults() : Flow<List<GetEmpResult>>

}