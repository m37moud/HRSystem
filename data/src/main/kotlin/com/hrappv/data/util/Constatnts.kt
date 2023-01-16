package util

import com.hrappv.GetAllEmployees
import com.hrappv.data.models.Employee
import com.hrappv.data.models.Employees
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.awt.Dialog
import java.awt.FileDialog
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files

class Constatnts {
    companion object {
        fun getMonth(mm: String): String {

            return when (mm.length) {
                1 -> {
                    "0".plus(mm.toString())

                }

                2 -> {
                    mm

                }

                else -> "please enter month : "
            }
        }

        fun getLastDay(month: Int, year: Int): Int {
            var day = 28
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                day = 31
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                day = 30
            } else if (month == 2 && year % 4 == 0) {
                day = 29
            }
            return day
        }

        fun getWardiaAsNum(wardia: String): Int {
            return when (wardia) {
                "اولى" -> {
                    1
                }

                "ثاني" -> {
                    2
                }

                "ثالثة" -> {
                    3
                }

                "اولى متاخر" -> {
                    1
                }

                else -> {
                    0
                }
            }
        }

        suspend fun excelImporter(path: String): List<Employees> {
            val empList = mutableListOf<Employees>()

            val pathList = getPath(path)


            try {

                pathList.let {it->
                    it?.forEach { path ->
                        val tempList = readFromExcelFile(path)
                        empList.addAll(tempList)
                    }
                }

            } catch (e: Exception) {
                println(e.message.toString())
                return emptyList()


            }

            return empList

        }

        fun getPath(path: String): List<String>? {
            val pathList = mutableListOf<String>()
            try {
                val file = File(path)
                if (file.isDirectory) {
                    if (file.list()?.isNotEmpty()!!) {
                        file.list()?.forEach { path ->
                            val sFile = file.path + File.separator + path
                            if (!Files.isDirectory(File(sFile).toPath()))
                                pathList.add(sFile)

                        }
                    }
                }

            } catch (e: Exception) {
                println(e.message.toString())
                return emptyList()


            }
            return pathList

        }

        private suspend fun readFromExcelFile(filePath: String): List<Employees> {//, inputDialog: FileDialog
            val empList = mutableListOf<Employees>()
            try {
                val inputStream = FileInputStream(filePath)
//            val file = File(filePath)
                //Instantiate Excel workbook using existing file:
                val xlWb = WorkbookFactory.create(inputStream)
//            val xlWb = WorkbookFactory.create(file)

                //Row index specifies the row in the worksheet (starting at 0):
                var rowNumber = 0
                //Cell index specifies the column within the chosen row (starting at 0):

                //Get reference to first sheet:
                val xlWs = xlWb.getSheetAt(0)
                var stop: String?
                println(xlWs.lastRowNum)
                xlWs.forEach { row ->
                    val emp = mutableListOf<String>()
                    if (row.rowNum >= 1) {
                        for (columnNumber in 0..2) {
                            val value = xlWs.getRow(row.rowNum).getCell(columnNumber).toString()
                            when (columnNumber) {
                                0 -> { // id
                                    emp.add(value.substringAfter("'"))
                                }

                                2 -> {//department
                                    val ll = value.split("/")
                                    emp.add(ll[1])

                                }

                                3 -> { // for
                                    val ll = value.split(" ")
                                    emp.add(ll[0])
                                    emp.add(ll[1])

                                }

                                else -> emp.add(value)
                            }

                        }

                        val employ = Employees(emp_id = emp[0].toLong(), fname = emp[1], department_name = emp[2])
                        empList.add(employ)


                    }


                }


            } catch (e: Exception) {
                println("import error : ${e.message}")
                return emptyList()

            }


            return empList
        }

    }


}