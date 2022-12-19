package com.hrappv.data.repo

import com.hrappv.data.local.datastoreimp.UserDataSourceImpl
import excel.ImportExcelFile
import javax.inject.Inject

class MyRepo @Inject constructor(
    private val importerExcel: ImportExcelFile,
    private val usersDataSource: UserDataSourceImpl,

    ) {
    fun getClickedWelcomeText() = "Hello Desktop!"
    val importer = importerExcel
    val users = usersDataSource



}