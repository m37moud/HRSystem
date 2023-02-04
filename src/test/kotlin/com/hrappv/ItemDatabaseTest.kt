import com.hrappv.HrAppDb
import com.hrappv.data.local.datastoreimp.CamRegisterDataSourceImpl
import com.hrappv.data.local.datastoreimp.DayRegisterDataSourceImpl
import com.hrappv.data.local.datastoreimp.DepartmentDataSourceImpl
import com.hrappv.data.local.datastoreimp.EmpResultDataSourceImpl
import com.hrappv.data.models.CamRegisterDay
import com.hrappv.data.models.Department
import com.hrappv.data.models.RegisterAttends
import com.hrappv.di.AppComponent
import com.hrappv.test.DaggerTestComponent
import com.hrappv.test.MainCoroutineRule
import com.hrappv.test.MyDaggerMockRule
import com.nhaarman.mockitokotlin2.mock
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import excel.ImportExcelFile
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Rule
import org.junit.Test
import util.Constatnts
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class ItemDatabaseTest {

    private val inMemorySqlDriver = JdbcSqliteDriver(url = "jdbc:sqlite:database.db", Properties()).apply {
        HrAppDb.Schema.create(this)
    }

    @get:Rule
    val coroutineRule = MainCoroutineRule()

//    @get:Rule
//    val daggerMockRule = MyDaggerMockRule()


    private val queries = HrAppDb(inMemorySqlDriver).departmentQueries
    private val empQueries = HrAppDb(inMemorySqlDriver).employeQueries
    private val registerQueries = HrAppDb(inMemorySqlDriver).day_registerQueries
    val camDayDataSource = CamRegisterDataSourceImpl(HrAppDb(inMemorySqlDriver), mock())
    val empResultDataSource = EmpResultDataSourceImpl(HrAppDb(inMemorySqlDriver), mock())
    val importer = ImportExcelFile()

    @Test
    fun empResult() {

        val cam = camDayDataSource.getAllCameraRegister()

        val listCam = importer.getEmployReport(cam)

//        val list =empResultDataSource.getAllEmployeeResults()
        if (listCam.isNotEmpty()) {
            println("list" + listCam[0].toString())
            empResultDataSource.insertMultiEmpResult(listCam)
        }
    }

    @Test
    fun camRegTest() { //successful

        val camDaysList = importer.getAllEmployeeInfo(path = "F:\\8")
//       println(camDaysList.joinToString("\n"))
        camDayDataSource.insertMultiCamRegDay(camDaysList)


//        CamRegisterDay(
//            empName = "Mohamed Elsaye Elsayed",
//            departName = "ديكور",
//            oDATE = "2022-07-22",
//            day =  LocalDate.parse("2022-07-22").dayOfWeek.toString(),
//            time = "07:37:30",
//            hour = "07",
//            status ="Check-out" //Check-out
//        ).apply {
//            camDayDataSource.insertCamRegisterDay(this)
//        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun smokeTest() {
//        val emptyItems = queries.getAllDepartments().executeAsList()
////        assertEquals(emptyItems.size, 0)
////        withContext(Dispatchers.IO) {
//        val d = DepartmentDataSourceImpl(HrAppDb(inMemorySqlDriver))
        val d = DayRegisterDataSourceImpl(HrAppDb(inMemorySqlDriver), mock())


//
        val day = RegisterAttends(
            emp_name = "Mohamed Elsaye Elsayed",
            department = "ديكور",
            oDate = "2022-07-22",
            day = LocalDate.parse("2022-07-22").dayOfWeek.toString(),
            status = "Attend",
            in_time = "07:37:30",
            out_time = null,
            late = null,
            early = null,
        )

        val dayUpdate = RegisterAttends(
            emp_name = "Mohamed Elsaye Elsayed",
            department = "ديكور",
            oDate = "2022-07-22",
            day = null,
            status = null,
            in_time = null,
            out_time = "15:52:16",
            late = null,
            early = null,
        )

//        coroutineRule.launch{
        val departmentList =
            Constatnts.registerDayExcelImporter("D:\\desk\\شغل لعهد\\tutorial audting\\2022\\شهراغسطس8\\8\\8")
//            println(departmentList.joinToString("\n"))

//          val result =  d.insertRegisterDay(dayUpdate)
        val result = d.insertMultiDaysRegister(departmentList)
        println("result = $result")

//        }
////        d.insertDepart(
////            depart
////        )
//        d.insertMultiDepartment(departmentList)
////        queries.transaction {
////            queries.insertDepartMent(
//////            depart_id = "Pineapple",
////                department = "a ",
////                commetion_rate = 15,
////                commetion_type = null,
////                depart_type = "",
////                commetion_month = "8",
//////                depart_id = null
////            )
////            afterRollback { println(" insert faild ") }
////            afterCommit { println("department insert") }
////        }
////        }
//
//
//        val items = queries.getAllDepartments().executeAsList()
//        println("department ${items}")
////        assertEquals(items.size, 1)
//
////        val pineappleItem = queries.selectRocketById("Pineapple").executeAsOneOrNull()
////        assertEquals(pineappleItem?.image, "https://localhost/pineapple.png")
////        assertEquals(pineappleItem?.quantity?.toInt(), 5)
    }


//    @Test
//    fun empTest() {
//        val emptyItems = empQueries.getAllEmployees().executeAsList()
////        assertEquals(emptyItems.size, 0)
//        empQueries.transaction {
//            empQueries.insertEmployee(
//            emp_id = 1,
//                department = "test3",
//            commetion_rate = 15,
//            commetion_type = "",
//            depart_type = "",
//            commetion_month = "8",
//            depart_id = 1
//            )
//            afterRollback { println(" insert faild ") }
//            afterCommit { println("department insert") }
//        }
//
//
//        val items = queries.getAllDepartments().executeAsList()
//        println("department ${items.toString()}")
//        assertEquals(items.size, 1)
//
////        val pineappleItem = queries.selectRocketById("Pineapple").executeAsOneOrNull()
////        assertEquals(pineappleItem?.image, "https://localhost/pineapple.png")
////        assertEquals(pineappleItem?.quantity?.toInt(), 5)
//    }
}
