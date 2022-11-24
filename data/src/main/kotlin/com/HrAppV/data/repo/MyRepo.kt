package com.HrAppV.data.repo

import com.HrAppV.data.di.module.Employee
import com.HrAppV.data.di.module.EmployeeResult
import excel.ImportExcelFile
import utils.LCE
import javax.inject.Inject

class MyRepo @Inject constructor(
    private val importerExcel: ImportExcelFile
    ) {
    fun getClickedWelcomeText() = "Hello Desktop!"
    val importer = importerExcel



}