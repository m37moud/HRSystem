package com.hrappv.data.repo

import com.hrappv.data.local.datastore.UserDataSource
import com.hrappv.data.local.datastore.ViewEmpDataSource
import com.hrappv.data.local.datastoreimp.UserDataSourceImpl
import excel.ImportExcelFile
import javax.inject.Inject

class MyRepo @Inject constructor(
    private val importerExcel: ImportExcelFile,
    private val usersDataSource: UserDataSource,
    private val viewEmpDataSource: ViewEmpDataSource,

    ) {
    fun getClickedWelcomeText() = "Hello Desktop!"
    val importer = importerExcel
    val users = usersDataSource
    val viewEmployees = viewEmpDataSource



}