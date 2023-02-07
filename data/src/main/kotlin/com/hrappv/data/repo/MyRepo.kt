package com.hrappv.data.repo

import com.hrappv.data.local.datastore.*
import com.hrappv.data.local.datastoreimp.CamRegisterDataSourceImpl
import excel.ImportExcelFile
import javax.inject.Inject

class MyRepo @Inject constructor(
//    private val importerExcel: ImportExcelFile,
    private val usersDataSource: UserDataSource,
    private val viewEmpDataSource: ViewEmpDataSource,
    private val departmentDataSource: DepartmentDataSource,
    private val camRegisterDataSource: CamRegisterDataSource,
    private val dayDetailsDataSource: DayDetailsDataSource,
    private val empResultDataSource: EmpResultDataSource,

    ) {
    fun getClickedWelcomeText() = "Hello Desktop!"
//    val importer = importerExcel
    val users = usersDataSource
    val viewEmployees = viewEmpDataSource
    val department = departmentDataSource
    val camRegister = camRegisterDataSource
    val empResult = empResultDataSource
    val dayDetails = dayDetailsDataSource


}