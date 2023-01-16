package com.hrappv.data.repo

import com.hrappv.data.local.datastore.DepartmentDataSource
import com.hrappv.data.local.datastore.UserDataSource
import com.hrappv.data.local.datastore.ViewEmpDataSource
import excel.ImportExcelFile
import javax.inject.Inject

class MyRepo @Inject constructor(
    private val importerExcel: ImportExcelFile,
    private val usersDataSource: UserDataSource,
    private val viewEmpDataSource: ViewEmpDataSource,
    private val departmentDataSource: DepartmentDataSource,

    ) {
    fun getClickedWelcomeText() = "Hello Desktop!"
    val importer = importerExcel
    val users = usersDataSource
    val viewEmployees = viewEmpDataSource
    val department = departmentDataSource


}