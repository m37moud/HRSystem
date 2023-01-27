package util

import com.hrappv.Day_register
import com.hrappv.data.models.Department
import com.hrappv.data.models.Employees
import com.hrappv.data.models.RegisterAttends
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

        suspend fun excelImporterDepartment(path: String): List<Department> {
            val departList = mutableListOf<Department>()

            val pathList = getPath(path)


            try {

                pathList.let {
                    it?.forEach { path ->
                        val tempList = readFromExcelFileDepartment(path)
                        departList.addAll(tempList)
                    }
                }

            } catch (e: Exception) {
                println(e.message.toString())
                return emptyList()


            }

            return departList

        }

        /**
         * start
         * register days from excel
         */

         fun registerDayExcelImporter(path: String): List<RegisterAttends> {
            val dayRegister = mutableListOf<RegisterAttends>()
            val pathList = getPath(path)
            try {

                pathList.let { it ->
                    it?.forEach { path ->
                        val tempList = registerDayExcelFile(path)
                        dayRegister.addAll(tempList)
                    }
                }

            } catch (e: Exception) {
                println(e.message.toString())
                return emptyList()


            }



            return dayRegister
        }

        private  fun registerDayExcelFile(filePath: String): List<RegisterAttends> {//, inputDialog: FileDialog
            val dayRegister = mutableListOf<RegisterAttends>()
            val dayRegisterMap = mutableMapOf<LocalDateTime, RegisterAttends>()
            val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            try {
//                val inputStream = withContext(Dispatchers.IO) {
                val inputStream = FileInputStream(filePath)
//                }
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
                    val status = mutableMapOf<String, String>()
                    if (row.rowNum >= 1) {
                        for (columnNumber in 0..4) {
                            val value = xlWs.getRow(row.rowNum).getCell(columnNumber).toString()
                            when (columnNumber) {
                                1 -> { // empName
                                    status["empName"] = value
                                }

                                2 -> {//department
                                    val ll = value.split("/")
                                    status["department"] = ll[1]

//                                    status.add(ll[1])

                                }

                                3 -> {//date
                                    val ll = value.split(" ")
                                    status["date"] = ll[0]
                                    status["timeIn"] = ll[1]
                                    status["timeOut"] = ll[1]

//                                    status.add(ll[0])
//                                    status.add(ll[1])

                                }

                                4 -> {//date
                                    status["attendState"] = value

                                }


                            }

                        }


                        val registerAttends = RegisterAttends(
                            emp_name = status["empName"],
                            department = status["department"],
                            oDate = status["date"],
                            day = null,
                            status = null,
                            in_time = if (status["attendState"].equals("Check-in")) status["timeIn"] else null,
                            out_time = if (status["attendState"].equals("Check-out")) status["timeOut"] else null,
                            late = null,
                            early = null,

                            )
//                        dayRegister.add(registerAttends)

//                        dayRegisterMap[LocalDateTime.parse(status["date"])] = registerAttends

                        if (!dayRegisterMap.containsKey(LocalDateTime.parse(status["date"]))) {
                            dayRegisterMap[LocalDateTime.parse(status["date"],pattern)] = registerAttends
                        } else {
                            dayRegisterMap[LocalDateTime.parse(status["date"],pattern)] = registerAttends
//                            dayRegisterMap.merge(LocalDateTime.parse(status["date"]) , registerAttends)
//
                        }


                    }


                }


            } catch (e: Exception) {
                println("import error : ${e.message}")
                return emptyList()

            }

            println(dayRegisterMap.toString())
            return dayRegister
        }


        /**
         * end
         * register days from excel
         */


        suspend fun empExcelImporter(path: String): List<Employees> {
            val empList = mutableListOf<Employees>()

            val pathList = getPath(path)


            try {

                pathList.let { it ->
                    it?.forEach { path ->
                        val tempList = addEmpExcelFile(path)
                        empList.addAll(tempList)
                    }
                }

            } catch (e: Exception) {
                println(e.message.toString())
                return emptyList()


            }

            return empList

        }

        private fun getPath(path: String): List<String>? {
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

        private suspend fun addEmpExcelFile(filePath: String): List<Employees> {//, inputDialog: FileDialog
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


                                else -> emp.add(value)
                            }

                        }

                        val employ = Employees(
                            emp_id = emp[0].toLong(),
//                            id = emp[0].toLong(),
                            fname = emp[1],
                            department_name = emp[2].toString()
                        )
                        empList.add(employ)


                    }


                }


            } catch (e: Exception) {
                println("import error : ${e.message}")
                return emptyList()

            }


            return empList
        }


        private suspend fun readFromExcelFileDepartment(filePath: String): List<Department> {//, inputDialog: FileDialog
            val departList = mutableListOf<Department>()
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
                    val depart = mutableListOf<String>()
                    if (row.rowNum >= 1) {
                        for (columnNumber in 0..3) {
                            val value = xlWs.getRow(row.rowNum).getCell(columnNumber).toString()
                            when (columnNumber) {

                                2 -> {
                                    val ll = value.split("/")
                                    depart.add(ll[1])
                                }

                            }

                        }

                        val department = Department(department = depart[0])
                        departList.add(department)


                    }


                }


            } catch (e: Exception) {
                println("import error : ${e.message}")
                return emptyList()

            }


            return departList
        }


    }


}