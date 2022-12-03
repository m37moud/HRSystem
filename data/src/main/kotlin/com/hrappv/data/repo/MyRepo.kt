package com.hrappv.data.repo

import excel.ImportExcelFile
import javax.inject.Inject

class MyRepo @Inject constructor(
    private val importerExcel: ImportExcelFile
    ) {
    fun getClickedWelcomeText() = "Hello Desktop!"
    val importer = importerExcel



}