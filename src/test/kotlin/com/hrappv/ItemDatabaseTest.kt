import com.hrappv.HrAppDb
import com.hrappv.data.local.datastoreimp.DepartmentDataSourceImpl
import com.hrappv.data.models.Department
import com.hrappv.di.AppComponent
import com.hrappv.test.DaggerTestComponent
import com.hrappv.test.MainCoroutineRule
import com.hrappv.test.MyDaggerMockRule
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Rule
import org.junit.Test
import util.Constatnts
import java.util.*

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


    @Test
    fun smokeTest() {
//        val emptyItems = queries.getAllDepartments().executeAsList()
////        assertEquals(emptyItems.size, 0)
////        withContext(Dispatchers.IO) {
//        val d = DepartmentDataSourceImpl(HrAppDb(inMemorySqlDriver))
//
//        val depart = Department(
//                department = "a ",
//        commetion_rate = 15,
//        commetion_type = null,
//        depart_type = "",
//        commetion_month = "8"
//        )
//        coroutineRule.launch {
            val departmentList =  Constatnts.registerDayExcelImporter("F:\\8")
        println(departmentList)
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
